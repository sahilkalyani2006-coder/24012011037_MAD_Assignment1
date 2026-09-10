#!/usr/bin/env python3
"""Static checks possible without the Android SDK."""
import re
import xml.etree.ElementTree as ET
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
APP = ROOT / "app/src/main"

for xml_file in (APP / "res").rglob("*.xml"):
    ET.parse(xml_file)
ET.parse(APP / "AndroidManifest.xml")

strings = ET.parse(APP / "res/values/strings.xml").getroot()
known_strings = {item.attrib["name"] for item in strings if item.tag == "string"}
used_strings = set()
for file in list(APP.rglob("*.xml")) + list((APP / "java").rglob("*.kt")):
    text = file.read_text()
    used_strings.update(re.findall(r"(?:@string/|R\.string\.)([A-Za-z0-9_]+)", text))
assert used_strings <= known_strings, f"missing strings: {sorted(used_strings - known_strings)}"

settings = (ROOT / "settings.gradle.kts").read_text()
manifest = (APP / "AndroidManifest.xml").read_text()
assert 'rootProject.name = "24012011037_MAD_Assignment1"' in settings
assert "android.permission.INTERNET" not in manifest
assert not list((APP / "java").rglob("MapLinks.kt"))
assert "google.com/maps" not in "\n".join(file.read_text() for file in (APP / "java").rglob("*.kt"))

print(f"OK: parsed {len(list(APP.rglob('*.xml')))} XML files and checked resources/project name")
