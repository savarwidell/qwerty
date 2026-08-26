"""
PDF Extractor - Herramienta para extraer texto de múltiples archivos PDF en una carpeta especificada.
Este script utiliza PyMuPDF y pdfplumber para extraer texto, con un modo de recuperación para PDFs problemáticos.

Requiere: Una carpeta con un PDF o multiples arhivos PDF.

Savar Widell
"""

# ------ CONFIGURACIONES ------

PDF_FOLDER = "pdfs/"        # Folder que contiene todos los PDF
NUM_THREADS = 8             # Número de hilos para procesamiento concurrente
SEARCH_WORD = ""   # Palabra clave para buscar en el texto extraído

# ------

from reqloader import install_if_missing
install_if_missing(['PyMuPDF', 'pdfplumber', 'tqdm', 'pandas', 'openpyxl', 'fitz'], "PDF Extractor")

import os
import fitz
import pdfplumber
from concurrent.futures import ThreadPoolExecutor, as_completed
from tqdm import tqdm
import time
import logging
from pathlib import Path
import json

logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(levelname)s - %(message)s',
    handlers=[
        logging.FileHandler('pdf_extraction.log'),
        logging.StreamHandler()
    ]
)

class MassivePDFExtractor:
    def __init__(self, pdf_folder, max_workers=4):
        self.folder = pdf_folder
        self.max_workers = max_workers
        self.results = {}
        self.errors = []
        self.stats = {
            'total': 0,
            'successful': 0,
            'failed': 0,
            'total_time': 0,
            'characters_extracted': 0
        }
        
    def extract_single_pdf(self, filename, filepath):
        name = filename
        text = None
        method_used = None
        
        methods = [
            ('PyMuPDF', self._extract_with_pymupdf),
            ('pdfplumber', self._extract_with_pdfplumber)
        ]
        
        for method_name, method_func in methods:
            try:
                text = method_func(filepath)
                if text and len(text.strip()) > 50:
                    method_used = method_name
                    break
            except Exception as e:
                logging.debug(f"Error with {method_name} in {name}: {e}")
                continue
        
        if not text or len(text.strip()) < 50:
            try:
                text = self._extract_recovery_mode(filepath)
                if text and len(text.strip()) > 50:
                    method_used = 'Recovery'
            except:
                pass
        
        return name, text, method_used
    
    def _extract_with_pymupdf(self, filepath):
        doc = fitz.open(filepath)
        try:
            text = []
            for page in doc:
                page_text = page.get_text()
                if page_text:
                    text.append(page_text)
            return ' '.join(text)
        finally:
            doc.close()
    
    def _extract_with_pdfplumber(self, filepath):
        with pdfplumber.open(filepath) as pdf:
            text = []
            for page in pdf.pages:
                page_text = page.extract_text()
                if page_text:
                    text.append(page_text)
            return ' '.join(text)
    
    def _extract_recovery_mode(self, filepath):
        try:
            doc = fitz.open(filepath)
            text = []
            for i, page in enumerate(doc):
                try:
                    page_text = page.get_text()
                    if page_text:
                        text.append(page_text)
                    else:
                        pix = page.get_pixmap()
                        logging.warning(f"Page {i+1} has no extractable text")
                except:
                    continue
            doc.close()
            return ' '.join(text)
        except:
            return None
    
    def extract_all(self, save_csv=False, save_json=False):
        pdf_files = [f for f in os.listdir(self.folder) 
                    if f.lower().endswith('.pdf')]
        
        if not pdf_files:
            logging.error(f"No PDFs found in {self.folder}")
            return {}
        
        self.stats['total'] = len(pdf_files)
        logging.info(f"Starting extraction of {len(pdf_files)} PDFs...")
        
        start_time = time.time()
        
        with ThreadPoolExecutor(max_workers=self.max_workers) as executor:
            futures = {
                executor.submit(self.extract_single_pdf, filename, 
                              os.path.join(self.folder, filename)): filename
                for filename in pdf_files
            }
            
            with tqdm(total=len(pdf_files), desc="Extracting PDFs") as pbar:
                for future in as_completed(futures):
                    name, text, method = future.result()
                    
                    if text and len(text.strip()) > 50:
                        self.results[name] = text
                        self.stats['successful'] += 1
                        self.stats['characters_extracted'] += len(text)
                        logging.info(f"[OK] {name} - {method} - {len(text)} characters")
                    else:
                        self.errors.append({
                            'file': name,
                            'reason': 'Insufficient or empty text'
                        })
                        self.stats['failed'] += 1
                        logging.warning(f"[FAIL] {name} - Could not extract valid text")
                    
                    pbar.update(1)
                    pbar.set_postfix({
                        'OK': self.stats['successful'],
                        'FAIL': self.stats['failed']
                    })
        
        self.stats['total_time'] = time.time() - start_time
        
        if save_csv:
            self._save_csv()
        
        if save_json:
            self._save_json()
        
        self._show_statistics()
        
        return self.results
    
    def _save_csv(self):
        import csv
        csv_file = 'extracted_texts.csv'
        with open(csv_file, 'w', newline='', encoding='utf-8') as f:
            writer = csv.writer(f)
            writer.writerow(['File', 'Text', 'Length'])
            for name, text in self.results.items():
                writer.writerow([name, text, len(text)])
        logging.info(f"CSV saved: {csv_file}")
    
    def _save_json(self):
        json_file = 'extracted_texts.json'
        with open(json_file, 'w', encoding='utf-8') as f:
            json.dump({
                'statistics': self.stats,
                'results': self.results,
                'errors': self.errors
            }, f, ensure_ascii=False, indent=2)
        logging.info(f"JSON saved: {json_file}")
    
    def _show_statistics(self):
        print("\n" + "="*60)
        print("EXTRACTION STATISTICS")
        print("="*60)
        print(f"Total PDFs: {self.stats['total']}")
        print(f"Successful: {self.stats['successful']}")
        print(f"Failed: {self.stats['failed']}")
        print(f"Characters extracted: {self.stats['characters_extracted']:,}")
        print(f"Total time: {self.stats['total_time']:.2f} seconds")
        print(f"Speed: {self.stats['successful']/self.stats['total_time']:.2f} PDFs/second")
        
        if self.errors:
            print(f"\n[WARNING] {len(self.errors)} PDFs with errors:")
            for error in self.errors[:5]:
                print(f"  - {error['file']}: {error['reason']}")
        
        print("="*60)
    
    def save_individual_texts(self, output_folder='extracted_texts'):
        os.makedirs(output_folder, exist_ok=True)
        
        for name, text in tqdm(self.results.items(), desc="Saving individual files"):
            base_name = Path(name).stem
            output_path = os.path.join(output_folder, f"{base_name}.txt")
            with open(output_path, 'w', encoding='utf-8') as f:
                f.write(text)
        
        logging.info(f"Individual files saved in: {output_folder}")
        print(f"\n[OK] {len(self.results)} individual files saved in: {output_folder}/")
    
    def save_combined_text(self, output_file='all_texts_combined.txt', 
                           include_separator=True, separator='='*80):
        if not self.results:
            logging.warning("No results to combine")
            return
        
        print(f"\n[INFO] Combining all texts into: {output_file}")
        
        with open(output_file, 'w', encoding='utf-8') as combined_file:
            combined_file.write(f"{'='*80}\n")
            combined_file.write(f"COMBINED TEXTS FROM {len(self.results)} PDFs\n")
            combined_file.write(f"Creation date: {time.strftime('%Y-%m-%d %H:%M:%S')}\n")
            combined_file.write(f"Total characters: {self.stats['characters_extracted']:,}\n")
            combined_file.write(f"{'='*80}\n\n")
            
            combined_file.write("FILE INDEX:\n")
            combined_file.write("-" * 80 + "\n")
            for i, (name, text) in enumerate(self.results.items(), 1):
                combined_file.write(f"{i:4d}. {name} - {len(text):,} characters\n")
            combined_file.write("\n" + "="*80 + "\n\n")
            
            for i, (name, text) in enumerate(self.results.items(), 1):
                if include_separator:
                    combined_file.write(f"\n{separator}\n")
                    combined_file.write(f"FILE [{i}/{len(self.results)}]: {name}\n")
                    combined_file.write(f"CHARACTERS: {len(text):,}\n")
                    combined_file.write(f"{separator}\n\n")
                
                combined_file.write(text)
                
                if not text.endswith('\n'):
                    combined_file.write('\n')
        
        file_size = os.path.getsize(output_file)
        if file_size > 1024 * 1024:
            size_str = f"{file_size / (1024 * 1024):.2f} MB"
        elif file_size > 1024:
            size_str = f"{file_size / 1024:.2f} KB"
        else:
            size_str = f"{file_size} bytes"
        
        logging.info(f"Combined file saved: {output_file} ({size_str})")
        print(f"[OK] Combined file saved: {output_file} ({size_str})")
        
        return output_file
    
    def save_all(self, individual_folder='extracted_texts', 
                 combined_file='all_texts_combined.txt',
                 include_separator=True):
        self.save_individual_texts(individual_folder)
        self.save_combined_text(combined_file, include_separator)
        
        print("\n" + "="*60)
        print("PROCESS COMPLETED")
        print("="*60)
        print(f"Individual files: {individual_folder}/")
        print(f"Combined file: {combined_file}")
        print("="*60)
    
    def search_by_word(self, word, case_sensitive=False):
        search_results = {}
        search_word = word if case_sensitive else word.lower()
        
        for name, text in self.results.items():
            compare_text = text if case_sensitive else text.lower()
            if search_word in compare_text:
                occurrences = compare_text.count(search_word)
                indices = [i for i in range(len(compare_text)) 
                          if compare_text.startswith(search_word, i)]
                contexts = []
                for idx in indices[:5]:
                    start = max(0, idx - 50)
                    end = min(len(text), idx + len(word) + 50)
                    context = text[start:end]
                    contexts.append(context)
                
                search_results[name] = {
                    'occurrences': occurrences,
                    'contexts': contexts
                }
        
        return search_results


if __name__ == "__main__":
    NUM_THREADS = 8
    
    print("\n" + "="*60)
    print("MASSIVE PDF EXTRACTOR")
    print("="*60)
    print(f"Folder: {PDF_FOLDER}")
    print(f"Threads: {NUM_THREADS}")
    print("="*60 + "\n")
    
    extractor = MassivePDFExtractor(
        pdf_folder=PDF_FOLDER,
        max_workers=NUM_THREADS
    )
    
    print("[INFO] Extracting text from PDFs...")
    results = extractor.extract_all(
        save_csv=True,
        save_json=True
    )
    
    if results:
        print("\n" + "="*60)
        print("SAVING TEXT FILES")
        print("="*60)
        
        extractor.save_all(
            individual_folder='extracted_texts',
            combined_file='all_texts_combined.txt',
            include_separator=True
        )
        
        print("\n" + "="*60)
        print("KEYWORD SEARCH")
        print("="*60)
        
        search_word = "important"
        print(f"\nSearching for '{search_word}'...")
        search = extractor.search_by_word(search_word)
        
        if search:
            print(f"[OK] Found in {len(search)} files:")
            for file, info in list(search.items())[:5]:
                print(f"\n  {file}: {info['occurrences']} occurrences")
                if info['contexts']:
                    print(f"    Context: ...{info['contexts'][0]}...")
            if len(search) > 5:
                print(f"  ... and {len(search) - 5} more files")
        else:
            print(f"[INFO] '{search_word}' not found in any file")
        
        try:
            import pandas as pd
            print("\n[INFO] Creating Excel file...")
            
            df = pd.DataFrame([
                {
                    'File': name, 
                    'Text (first 1000 characters)': text[:1000], 
                    'Total Length': len(text)
                }
                for name, text in results.items()
            ])
            
            df.to_excel('extracted_texts.xlsx', index=False)
            print("[OK] Excel file created: extracted_texts.xlsx")
        except ImportError:
            print("\n[WARNING] To save as Excel, install: pip install pandas openpyxl")
        except Exception as e:
            print(f"\n[WARNING] Error creating Excel: {e}")
    
    else:
        print("\n[ERROR] No text extracted from any PDF. Please check the folder.")
    
    print("\n" + "="*60)
    print("PROCESS COMPLETED")
    print("="*60)