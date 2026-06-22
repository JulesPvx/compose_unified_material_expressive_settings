import re
import os
import sys

# Allow overriding paths via environment variables for CI/CD flexibility
SOURCE_FILE = os.environ.get("SOURCE_FILE", "compose-settings-ui/src/main/java/fr/paeelluu/compose_settings_ui/SettingsSectionScope.kt")
WIKI_COMPONENTS_FILE = os.environ.get("WIKI_COMPONENTS_FILE", "../compose_unified_material_expressive_settings.wiki/Components.md")

def parse_kdoc_and_methods(file_path):
    if not os.path.exists(file_path):
        print(f"Error: Source file not found at {file_path}")
        sys.exit(1)
        
    with open(file_path, 'r') as f:
        content = f.read()

    # This regex ensures we only match the KDoc block directly preceding a 'public fun'
    pattern = r"/\*\*\s*((?:(?!\*/).)*?)\s*\*/\s*(?:@[\w\s]+\s+)?public fun (?:<.*?> )?(\w+)\((.*?)\)"
    matches = re.finditer(pattern, content, re.DOTALL)

    categories = {}

    for match in matches:
        kdoc = match.group(1)
        name = match.group(2)
        params_raw = match.group(3)

        # Extract description and category
        lines = [l.strip().replace("* ", "").strip() for l in kdoc.split('\n')]
        description_lines = []
        category = "Other"
        
        for line in lines:
            if line.startswith("@category"):
                category = line.replace("@category", "").strip()
            elif not line.startswith("@") and line:
                description_lines.append(line)

        description = " ".join(description_lines).strip()

        if category not in categories:
            categories[category] = []
        
        categories[category].append({
            "name": name,
            "description": description,
            "params": params_raw.strip()
        })

    return categories

def get_existing_examples(wiki_path):
    if not os.path.exists(wiki_path):
        print(f"Warning: Wiki file not found at {wiki_path}. Creating new file.")
        return {}
    
    with open(wiki_path, 'r') as f:
        content = f.read()
    
    examples = {}
    pattern = r"### (\w+)\n(.*?)\n```kotlin\n(.*?)\n```"
    matches = re.finditer(pattern, content, re.DOTALL)
    for match in matches:
        examples[match.group(1)] = match.group(3).strip()
    
    return examples

def generate_wiki():
    categories = parse_kdoc_and_methods(SOURCE_FILE)
    examples = get_existing_examples(WIKI_COMPONENTS_FILE)
    
    output = "# Components Catalog\n\nAll components are available within the `SettingsSectionScope`.\n\n"
    
    order = ["Basic Actions", "Toggles", "Input", "Selection", "Pickers", "Sliders", "Structure & Layout"]
    
    for cat in order:
        if cat in categories:
            output += f"## {cat}\n\n"
            for item in categories[cat]:
                name = item['name']
                desc = item['description']
                output += f"### {name}\n"
                output += f"{desc}\n"
                
                if name in examples:
                    output += "```kotlin\n" + examples[name] + "\n```\n\n"
                else:
                    output += "```kotlin\n// Example for " + name + " coming soon\n```\n\n"
            
            output += "---\n\n"
            del categories[cat]
            
    for cat, items in categories.items():
        output += f"## {cat}\n\n"
        for item in items:
            output += f"### {item['name']}\n{item['description']}\n"
            if item['name'] in examples:
                output += "```kotlin\n" + examples[item['name']] + "\n```\n\n"
        output += "---\n\n"

    # Ensure directory exists
    os.makedirs(os.path.dirname(WIKI_COMPONENTS_FILE), exist_ok=True)

    with open(WIKI_COMPONENTS_FILE, 'w') as f:
        f.write(output.strip() + "\n")

if __name__ == "__main__":
    generate_wiki()
    print(f"Successfully updated {WIKI_COMPONENTS_FILE} from {SOURCE_FILE}")
