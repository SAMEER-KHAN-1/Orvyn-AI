import re

main_re = re.compile(r"(?i)^\s*(?:remind me to|set a reminder to|set reminder to|set a reminder for|set reminder for)\s+(.+?)\s*\.?\s*$")

def parse(text):
    m = main_re.search(text)
    if not m:
        return "Fail Main"
    main = m.group(1)
    
    time_re = re.compile(r"(?i)\bat\s+(\d{1,2}(?::\d{2})?\s*(?:am|pm)?)\b")
    t = time_re.search(main)
    if not t:
        return f"Fail Time on: '{main}'"
    
    return f"Success! Task: {main}, Time: {t.group(1)}"

print("1.", parse("Set a reminder to go to the store at 5 pm"))
print("2.", parse("Set a reminder to find my keys at 5 pm"))
print("3.", parse("set a reminder to whatever at 5 pm tomorrow"))
print("4.", parse("Set a reminder for dinner at 8 pm"))
print("5.", parse("can you set a reminder to do this at 5 pm"))
print("6.", parse("Set a reminder to do this in 5 minutes"))
