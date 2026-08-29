"""
REUSABLE AUTOMATIC DEPENDENCY INSTALLER

Savar Widell
"""
import subprocess
import sys
import importlib
import os
from typing import List, Optional

class DependencyInstaller:
    """Reusable class for automatically installing dependencies"""
    
    def __init__(self, app_name: str = "Application"):
        self.app_name = app_name
        self.installed = []
        self.missing = []
    
    def verify_dependencies(self, dependencies: List[str]) -> bool:
        """
        Verifies and installs the specified dependencies
        
        Args:
            dependencies: List of package names to install
        
        Returns:
            True if all dependencies are installed
        """
        self.missing = []
        self.installed = []
        
        print(f"\n{'='*50}")
        print(f"VERIFYING DEPENDENCIES FOR {self.app_name.upper()}")
        print(f"{'='*50}")
        
        for package in dependencies:
            try:
                importlib.import_module(package)
                self.installed.append(package)
                print(f"[OK] {package} - Installed")
            except ImportError:
                self.missing.append(package)
                print(f"[MISSING] {package} - Not installed")
        
        if self.missing:
            print(f"\n[WARNING] {len(self.missing)} dependencies missing")
            self._install_dependencies()
            return True
        else:
            print(f"\n[OK] All dependencies are installed")
            return True
    
    def _install_dependencies(self):
        """Installs the missing dependencies"""
        print(f"\n[INFO] Installing missing dependencies...")
        print(f"{'='*50}")
        
        for package in self.missing:
            try:
                print(f"[INSTALLING] {package}...")
                subprocess.check_call([
                    sys.executable,
                    "-m",
                    "pip",
                    "install",
                    package,
                    "--upgrade",
                    "--quiet"
                ])
                print(f"[OK] {package} installed successfully")
            except subprocess.CalledProcessError as e:
                print(f"[ERROR] Failed to install {package}: {e}")
                print(f"[MANUAL] Please install manually: pip install {package}")
                sys.exit(1)
        
        print(f"\n{'='*50}")
        print(f"[OK] All dependencies installed successfully")
        print(f"[RESTARTING] {self.app_name}...")
        print(f"{'='*50}\n")
        
        subprocess.Popen([sys.executable] + sys.argv)
        sys.exit(0)


# SIMPLIFIED FUNCTION (for quick use)
def install_if_missing(dependencies: List[str], app_name: str = "Application"):
    """Quick function to verify and install dependencies"""
    installer = DependencyInstaller(app_name)
    installer.verify_dependencies(dependencies)


# FUNCTION FOR REQUIREMENTS FROM FILE
def install_from_requirements(file_path: str = "requirements.txt", app_name: str = "Application"):
    """Installs dependencies from a requirements.txt file"""
    if not os.path.exists(file_path):
        print(f"[WARNING] File not found: {file_path}")
        return False
    
    with open(file_path, 'r') as f:
        dependencies = [line.strip() for line in f if line.strip() and not line.startswith('#')]
    
    installer = DependencyInstaller(app_name)
    return installer.verify_dependencies(dependencies)


# USAGE EXAMPLE
if __name__ == "__main__":
    # Example: install dependencies for this file
    install_if_missing(['PyQt6', 'keyboard'], "Central Installer")