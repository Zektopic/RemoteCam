import sys
import re

with open(".Jules/palette.md", "r") as f:
    text = f.read()

# try to find any mention of "Unknown" or "latency"
for m in re.finditer(r'(?i)(latency)', text):
    start = max(0, m.start() - 200)
    end = min(len(text), m.end() + 200)
    print(text[start:end])
    print("-" * 40)
