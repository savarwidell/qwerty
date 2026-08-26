"""
Ventana oculta con información de texto, pestañas y temas, activada por combinaciones de teclas.

Requisitos: 
    Un archivo de texto llamado "info.txt" en la misma carpeta que este programa.

Dependencias: PyQt6, keyboard (se instalarán automáticamente si faltan).

Las pestañas se crean solo si el inicio de linea comienza con ### (TAB_MARKER), modificable abajo.

Savar Widell
"""

from reqloader import install_if_missing

install_if_missing(["PyQt6", "keyboard"], app_name="HiddenInfoApp")

# ============================================
# MAIN APPLICATION CODE
# ============================================
import sys
import os
import re
from PyQt6.QtWidgets import (QApplication, QLabel, QWidget, QVBoxLayout,
                             QScrollArea, QPushButton, QFrame, QHBoxLayout)
from PyQt6.QtCore import Qt, QTimer, QPoint, QEvent
from PyQt6.QtGui import QFont, QScreen, QMouseEvent
import keyboard


#  -------- CONFIGURACIONES --------

SHOW_KEYS = ['w', 'e', 'r']                # Teclas para mostrar la ventana (todas deben presionarse simultáneamente)
CLOSE_KEYS = ['j', 'k', 'l']               # Teclas para cerrar la ventana (todas deben presionarse simultáneamente)
THEME_KEY = 'c'                            # Tecla para cambiar el tema (presionar mientras la ventana está visible)
WINDOW_X = 0                               # Posición inicial X (0 = izquierda)
WINDOW_Y = 0                               # Posición inicial Y (0 = arriba)
WINDOW_WIDTH = None                        # None = Usar la mitad del ancho de la pantalla
WINDOW_HEIGHT = None                       # None = Usar la mitad del alto de la pantalla
FONT_FAMILY = "Arial"                      # Fuente para el texto
FONT_SIZE = 14                             # Tamaño de fuente para el texto
FONT_WEIGHT = QFont.Weight.Normal          # Peso de fuente (Normal, Bold, etc.)
TEXT_FILE = "info.txt"                     # Archivo de texto que contiene la información a mostrar (debe estar en la misma carpeta que este programa)
TAB_MARKER = "###"                         # Marca al inicio de la línea para crear una nueva pestaña (modificable)
MIN_WIDTH = 200                            # Ancho mínimo de la ventana
MIN_HEIGHT = 150                           # Alto mínimo de la ventana

# --------

class ToggleButton(QPushButton):
    def __init__(self, title, content, parent=None):
        super().__init__(parent)
        self.title = title
        self.content = content
        self.visible = False
        self.setFixedHeight(40)
        self.setStyleSheet("""
            QPushButton {
                background-color: rgba(60, 60, 60, 150);
                color: white;
                border: 1px solid #555;
                border-radius: 5px;
                text-align: left;
                padding: 5px 15px;
                font-weight: bold;
                font-size: 14px;
            }
            QPushButton:hover {
                background-color: rgba(80, 80, 80, 200);
            }
            QPushButton:checked {
                background-color: rgba(40, 40, 40, 200);
            }
        """)
        self.setText(f"▶  {self.title}")
        self.clicked.connect(self.toggle)
        
        self.content_widget = QWidget()
        self.content_widget.setVisible(False)
        self.content_widget.setStyleSheet("""
            QWidget {
                background-color: rgba(0, 0, 0, 0);
                padding: 10px 20px;
            }
        """)
        
        layout = QVBoxLayout()
        layout.setContentsMargins(20, 5, 20, 5)
        
        self.content_label = QLabel()
        self.content_label.setWordWrap(True)
        self.content_label.setAlignment(Qt.AlignmentFlag.AlignLeft | Qt.AlignmentFlag.AlignTop)
        self.content_label.setStyleSheet("""
            QLabel {
                color: #cccccc;
                padding: 10px;
                background-color: rgba(0, 0, 0, 0);
                font-size: 13px;
            }
        """)
        self.content_label.setText(self.content)
        
        layout.addWidget(self.content_label)
        self.content_widget.setLayout(layout)
    
    def toggle(self):
        self.visible = not self.visible
        self.content_widget.setVisible(self.visible)
        self.setText(f"{'▼' if self.visible else '▶'}  {self.title}")
        self.setChecked(self.visible)
    
    def get_content_widget(self):
        return self.content_widget
    
    def apply_button_theme(self, theme):
        self.setStyleSheet(f"""
            QPushButton {{
                background-color: {theme['button_bg']};
                color: {theme['button_text']};
                border: 1px solid {theme['button_border']};
                border-radius: 5px;
                text-align: left;
                padding: 5px 15px;
                font-weight: bold;
                font-size: 14px;
            }}
            QPushButton:hover {{
                background-color: {theme['button_bg_hover']};
            }}
            QPushButton:checked {{
                background-color: {theme['button_bg_checked']};
            }}
        """)
        
        self.content_label.setStyleSheet(f"""
            QLabel {{
                color: {theme['content_text']};
                padding: 10px;
                background-color: rgba(0, 0, 0, 0);
                font-size: 13px;
            }}
        """)
        
        self.content_widget.setStyleSheet(f"""
            QWidget {{
                background-color: rgba(0, 0, 0, 0);
                padding: 10px 20px;
            }}
        """)

class HiddenWindow(QWidget):
    def __init__(self):
        super().__init__()
        
        self.SHOW_KEYS = SHOW_KEYS
        self.CLOSE_KEYS = CLOSE_KEYS
        self.THEME_KEY = THEME_KEY
        self.MIN_WIDTH = MIN_WIDTH
        self.MIN_HEIGHT = MIN_HEIGHT
        
        screen = QApplication.primaryScreen()
        geometry = screen.geometry()
        
        self.WINDOW_X = WINDOW_X
        self.WINDOW_Y = WINDOW_Y
        
        if WINDOW_WIDTH is None:
            self.WINDOW_WIDTH = geometry.width() // 2
        else:
            self.WINDOW_WIDTH = WINDOW_WIDTH
        
        if WINDOW_HEIGHT is None:
            self.WINDOW_HEIGHT = geometry.height()
        else:
            self.WINDOW_HEIGHT = WINDOW_HEIGHT
        
        self.FONT_FAMILY = FONT_FAMILY
        self.FONT_SIZE = FONT_SIZE
        self.FONT_WEIGHT = FONT_WEIGHT
        
        self.themes = [
            {
                'name': 'Light',
                'bg': 'rgba(255, 255, 255, 255)',
                'text': 'black',
                'border': '#888',
                'border_visible': True,
                'button_bg': 'rgba(200, 200, 200, 200)',
                'button_text': 'black',
                'button_border': '#888',
                'button_bg_hover': 'rgba(220, 220, 220, 220)',
                'button_bg_checked': 'rgba(180, 180, 180, 220)',
                'content_text': 'black'
            },
            {
                'name': 'Dark',
                'bg': 'rgba(0, 0, 0, 255)',
                'text': 'white',
                'border': '#444',
                'border_visible': True,
                'button_bg': 'rgba(60, 60, 60, 150)',
                'button_text': 'white',
                'button_border': '#555',
                'button_bg_hover': 'rgba(80, 80, 80, 200)',
                'button_bg_checked': 'rgba(40, 40, 40, 200)',
                'content_text': '#cccccc'
            },
            {
                'name': 'Light Transparent',
                'bg': 'rgba(255, 255, 255, 180)',
                'text': 'black',
                'border': '#888',
                'border_visible': True,
                'button_bg': 'rgba(200, 200, 200, 150)',
                'button_text': 'black',
                'button_border': '#888',
                'button_bg_hover': 'rgba(220, 220, 220, 180)',
                'button_bg_checked': 'rgba(180, 180, 180, 180)',
                'content_text': 'black'
            },
            {
                'name': 'Dark Transparent',
                'bg': 'rgba(0, 0, 0, 180)',
                'text': 'white',
                'border': '#444',
                'border_visible': True,
                'button_bg': 'rgba(60, 60, 60, 120)',
                'button_text': 'white',
                'button_border': '#555',
                'button_bg_hover': 'rgba(80, 80, 80, 160)',
                'button_bg_checked': 'rgba(40, 40, 40, 160)',
                'content_text': '#cccccc'
            },
            {
                'name': 'Fully Transparent (Black Text)',
                'bg': 'rgba(0, 0, 0, 0)',
                'text': 'black',
                'border': 'transparent',
                'border_visible': False,
                'button_bg': 'rgba(200, 200, 200, 80)',
                'button_text': 'black',
                'button_border': 'rgba(100, 100, 100, 50)',
                'button_bg_hover': 'rgba(220, 220, 220, 120)',
                'button_bg_checked': 'rgba(180, 180, 180, 100)',
                'content_text': 'black'
            },
            {
                'name': 'Fully Transparent (White Text)',
                'bg': 'rgba(0, 0, 0, 0)',
                'text': 'white',
                'border': 'transparent',
                'border_visible': False,
                'button_bg': 'rgba(60, 60, 60, 80)',
                'button_text': 'white',
                'button_border': 'rgba(100, 100, 100, 50)',
                'button_bg_hover': 'rgba(80, 80, 80, 120)',
                'button_bg_checked': 'rgba(40, 40, 40, 100)',
                'content_text': '#cccccc'
            }
        ]
        
        self.current_theme = 0
        
        self.setWindowFlags(
            Qt.WindowType.FramelessWindowHint |
            Qt.WindowType.WindowStaysOnTopHint |
            Qt.WindowType.Tool
        )
        self.setAttribute(Qt.WidgetAttribute.WA_TranslucentBackground)
        
        self.setGeometry(self.WINDOW_X, self.WINDOW_Y, self.WINDOW_WIDTH, self.WINDOW_HEIGHT)
        self.hide()
        
        self.setup_ui()
        self.load_text_from_file()
        self.apply_theme()
        
        self.timer = QTimer()
        self.timer.timeout.connect(self.check_keys)
        self.timer.start(50)
        
        self.showing = False
        self.theme_key_pressed_prev = False
        
        self.dragging = False
        self.drag_position = QPoint()
        self.resizing = False
        self.resize_corner = None
        self.resize_start_pos = QPoint()
        self.resize_start_geometry = None
    
    def setup_ui(self):
        layout = QVBoxLayout()
        layout.setContentsMargins(20, 20, 20, 20)
        
        self.scroll_area = QScrollArea()
        self.scroll_area.setWidgetResizable(True)
        self.scroll_area.viewport().setAutoFillBackground(False)
        self.scroll_area.viewport().installEventFilter(self)
        
        self.scroll_area.setStyleSheet("""
            QScrollArea {
                border-radius: 10px;
                background: transparent;
            }
            QScrollBar:vertical {
                background: rgba(50, 50, 50, 100);
                width: 12px;
                border-radius: 6px;
            }
            QScrollBar::handle:vertical {
                border-radius: 6px;
                min-height: 20px;
            }
            QScrollBar::add-line:vertical, QScrollBar::sub-line:vertical {
                border: none;
                background: none;
            }
        """)
        
        self.content_widget = QWidget()
        self.content_widget.setStyleSheet("background: transparent;")
        self.content_layout = QVBoxLayout()
        self.content_layout.setSpacing(5)
        self.content_layout.setContentsMargins(10, 10, 10, 10)
        self.content_layout.setAlignment(Qt.AlignmentFlag.AlignTop)
        
        self.content_widget.setLayout(self.content_layout)
        self.scroll_area.setWidget(self.content_widget)
        layout.addWidget(self.scroll_area)
        self.setLayout(layout)
    
    def eventFilter(self, obj, event):
        if obj != self.scroll_area.viewport():
            return super().eventFilter(obj, event)
        
        if event.type() == QEvent.Type.Wheel:
            self.scroll_area.wheelEvent(event)
            return True
        
        if event.type() == QEvent.Type.MouseButtonPress:
            if event.button() == Qt.MouseButton.LeftButton:
                self.dragging = True
                self.drag_position = event.globalPosition().toPoint() - self.frameGeometry().topLeft()
                return True
            
            elif event.button() == Qt.MouseButton.RightButton:
                self.resizing = True
                self.resize_corner = self.get_quadrant(event.position().toPoint())
                self.resize_start_pos = event.globalPosition().toPoint()
                self.resize_start_geometry = self.geometry()
                return True
        
        if event.type() == QEvent.Type.MouseMove:
            if not self.dragging and not self.resizing:
                pos = event.position().toPoint()
                quadrant = self.get_quadrant(pos)
                
                if quadrant in ['top-left', 'bottom-right']:
                    self.setCursor(Qt.CursorShape.SizeFDiagCursor)
                elif quadrant in ['top-right', 'bottom-left']:
                    self.setCursor(Qt.CursorShape.SizeBDiagCursor)
                else:
                    self.setCursor(Qt.CursorShape.ArrowCursor)
                return True
            
            if self.dragging:
                new_pos = event.globalPosition().toPoint() - self.drag_position
                self.move(new_pos)
                return True
            
            if self.resizing and self.resize_corner:
                current_pos = event.globalPosition().toPoint()
                delta = current_pos - self.resize_start_pos
                
                x = self.resize_start_geometry.x()
                y = self.resize_start_geometry.y()
                width = self.resize_start_geometry.width()
                height = self.resize_start_geometry.height()
                
                if 'left' in self.resize_corner:
                    new_width = width - delta.x()
                    if new_width >= self.MIN_WIDTH:
                        x = self.resize_start_geometry.x() + delta.x()
                        width = new_width
                    else:
                        x = self.resize_start_geometry.x() + self.resize_start_geometry.width() - self.MIN_WIDTH
                        width = self.MIN_WIDTH
                
                if 'right' in self.resize_corner:
                    new_width = width + delta.x()
                    if new_width >= self.MIN_WIDTH:
                        width = new_width
                    else:
                        width = self.MIN_WIDTH
                
                if 'top' in self.resize_corner:
                    new_height = height - delta.y()
                    if new_height >= self.MIN_HEIGHT:
                        y = self.resize_start_geometry.y() + delta.y()
                        height = new_height
                    else:
                        y = self.resize_start_geometry.y() + self.resize_start_geometry.height() - self.MIN_HEIGHT
                        height = self.MIN_HEIGHT
                
                if 'bottom' in self.resize_corner:
                    new_height = height + delta.y()
                    if new_height >= self.MIN_HEIGHT:
                        height = new_height
                    else:
                        height = self.MIN_HEIGHT
                
                self.setGeometry(x, y, width, height)
                return True
        
        if event.type() == QEvent.Type.MouseButtonRelease:
            self.dragging = False
            self.resizing = False
            self.resize_corner = None
            self.setCursor(Qt.CursorShape.ArrowCursor)
            return True
        
        return False
    
    def load_text_from_file(self):
        text = ""
        
        try:
            if os.path.exists(TEXT_FILE):
                with open(TEXT_FILE, 'r', encoding='utf-8') as f:
                    text = f.read()
            else:
                self.show_error(f"File not found: {TEXT_FILE}\n\nPlease create a file named '{TEXT_FILE}' in the same folder as the program.")
                return
        except Exception as e:
            self.show_error(f"Error reading file {TEXT_FILE}:\n\n{str(e)}")
            return
        
        if not text or text.strip() == "":
            self.show_error(f"The file {TEXT_FILE} is empty\n\nPlease add content to the file.")
            return
        
        self.create_tabs(text)
    
    def show_error(self, message):
        self.clear_layout()
        
        error_label = QLabel(message)
        error_label.setAlignment(Qt.AlignmentFlag.AlignCenter)
        error_label.setWordWrap(True)
        error_label.setStyleSheet("""
            QLabel {
                color: #ff6b6b;
                padding: 40px;
                font-size: 16px;
                background-color: rgba(0, 0, 0, 0);
            }
        """)
        self.content_layout.addWidget(error_label)
    
    def clear_layout(self):
        while self.content_layout.count():
            item = self.content_layout.takeAt(0)
            if item.widget():
                item.widget().deleteLater()
    
    def create_tabs(self, text):
        self.clear_layout()
        
        lines = text.split('\n')
        sections = []
        current_title = None
        current_content = []
        
        for line in lines:
            clean_line = line.strip()
            
            if clean_line.startswith(TAB_MARKER):
                if current_title is not None and current_content:
                    sections.append((current_title, '\n'.join(current_content).strip()))
                
                current_title = clean_line[len(TAB_MARKER):].strip()
                current_content = []
            else:
                if current_title is not None:
                    current_content.append(line)
                else:
                    current_title = "Information"
                    current_content.append(line)
        
        if current_title is not None and current_content:
            sections.append((current_title, '\n'.join(current_content).strip()))
        
        if not sections:
            sections.append(("Information", text))
        
        self.buttons = []
        for title, content in sections:
            btn = ToggleButton(title, content)
            self.content_layout.addWidget(btn)
            self.content_layout.addWidget(btn.get_content_widget())
            self.buttons.append(btn)
        
        self.apply_button_theme()
    
    def apply_button_theme(self):
        current_theme = self.themes[self.current_theme]
        if hasattr(self, 'buttons'):
            for btn in self.buttons:
                btn.apply_button_theme(current_theme)
    
    def apply_theme(self):
        theme = self.themes[self.current_theme]
        
        if theme['border_visible']:
            border_style = f"border: 2px solid {theme['border']};"
        else:
            border_style = "border: none;"
        
        self.scroll_area.setStyleSheet(f"""
            QScrollArea {{
                {border_style}
                border-radius: 10px;
                background: transparent;
            }}
            
            QScrollBar:vertical {{
                background: rgba(50, 50, 50, 100);
                width: 12px;
                border-radius: 6px;
            }}
            QScrollBar::handle:vertical {{
                background-color: {theme['border'] if theme['border_visible'] else 'rgba(100, 100, 100, 50)'};
                border-radius: 6px;
                min-height: 20px;
            }}
            QScrollBar::handle:vertical:hover {{
                background-color: {self.adjust_color(theme['border'], 30) if theme['border_visible'] else 'rgba(150, 150, 150, 80)'};
            }}
            QScrollBar::add-line:vertical, QScrollBar::sub-line:vertical {{
                border: none;
                background: none;
            }}
        """)
        
        self.scroll_area.viewport().setStyleSheet(f"""
            background-color: {theme['bg']};
        """)
        
        self.apply_button_theme()
    
    def adjust_color(self, color_hex, amount):
        if color_hex.startswith('#'):
            return '#888'
        return color_hex
    
    def change_theme(self):
        self.current_theme = (self.current_theme + 1) % len(self.themes)
        self.apply_theme()
    
    def check_keys(self):
        show_pressed = all(keyboard.is_pressed(key) for key in self.SHOW_KEYS)
        close_pressed = all(keyboard.is_pressed(key) for key in self.CLOSE_KEYS)
        
        if close_pressed:
            self.close_app()
            return
        
        if show_pressed:
            if not self.showing:
                self.show()
                self.showing = True
                self.raise_()
                self.activateWindow()
            
            theme_pressed = keyboard.is_pressed(self.THEME_KEY)
            if theme_pressed and not self.theme_key_pressed_prev:
                self.change_theme()
                self.theme_key_pressed_prev = True
            elif not theme_pressed:
                self.theme_key_pressed_prev = False
        else:
            if self.showing:
                self.hide()
                self.showing = False
                self.theme_key_pressed_prev = False
    
    def close_app(self):
        self.hide()
        self.showing = False
        QApplication.quit()
        sys.exit(0)
    
    def get_quadrant(self, pos):
        width = self.width()
        height = self.height()
        x = pos.x()
        y = pos.y()
        
        half_width = width // 2
        half_height = height // 2
        
        if x < half_width and y < half_height:
            return 'top-left'
        elif x >= half_width and y < half_height:
            return 'top-right'
        elif x < half_width and y >= half_height:
            return 'bottom-left'
        else:
            return 'bottom-right'

def main():
    app = QApplication(sys.argv)
    app.setQuitOnLastWindowClosed(False)
    window = HiddenWindow()
    sys.exit(app.exec())

if __name__ == "__main__":
    main()