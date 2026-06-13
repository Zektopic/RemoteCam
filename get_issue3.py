import sys
with open(".Jules/palette.md", "r") as f:
    text = f.read()

import re
for m in re.finditer(r'(?i)(placeholder)', text):
    start = max(0, m.start() - 200)
    end = min(len(text), m.end() + 200)
    print(text[start:end])
    print("-" * 40)
