with open(".Jules/palette.md", "r") as f:
    lines = f.readlines()

for i, line in enumerate(lines):
    if "initial state" in line.lower() or "placeholder" in line.lower() or "unknown" in line.lower():
        print("".join(lines[i-2:i+5]))
