#  '||''''|                                                  ||                       .|'''.|            .     .    ||
#   ||  .    ... ... ... ...  ... ..    ....   ....   ....  ...  .... ...   ....      ||..  '    ....  .||.  .||.  ...  .. ...     ... .  ....
#   ||''|     '|..'   ||'  ||  ||' '' .|...|| ||. '  ||. '   ||   '|.  |  .|...||      ''|||.  .|...||  ||    ||    ||   ||  ||   || ||  ||. '
#   ||         .|.    ||    |  ||     ||      . '|.. . '|..  ||    '|.|   ||         .     '|| ||       ||    ||    ||   ||  ||    |''   . '|..
#  .||.....| .|  ||.  ||...'  .||.     '|...' |'..|' |'..|' .||.    '|     '|...'    |'....|'   '|...'  '|.'  '|.' .||. .||. ||.  '||||. |'..|'
#                     ||                                                                                                         .|....'
#                    ''''
#
#  Copyright (c) 2026 Jules Pouvreaux
#
#  This file is part of the Compose Unified Material Expressive Settings Library.
#  Licensed under the Proprietary License.
#  All rights reserved.

import os
import re
import shutil
import time

WIKI_PATH = "wiki"
COMPONENTS_FILE = os.path.join(WIKI_PATH, "Components.md")
HOME_FILE = os.path.join(WIKI_PATH, "Home.md")
SNAPSHOTS_PATH = "sample/src/test/snapshots/images"
SOURCE_FILE = "compose-settings-ui/src/main/java/fr/paeelluu/compose_settings_ui/SettingsSectionScope.kt"

# Mapping between Component Name and the Snapshot file name suffix
COMPONENT_MAPPING = {
    "action": "actionItem",
    "link": "linkItem",
    "userProfile": "userProfileItem",
    "switch": "switchItem",
    "checkbox": "checkboxItem",
    "textField": "textFieldItem",
    "stepper": "stepperItem",
    "keywordEditor": "keywordEditorItem",
    "segmentedButton": "segmentedButtonItem",
    "selector": "selectorItem",
    "dialogSelector": "dialogSelectorItem",
    "radioButtonGroup": "radioButtonGroupItem",
    "multiSelectList": "multiSelectListItem",
    "timePicker": "timePickerItem",
    "datePicker": "datePickerItem",
    "colorPicker": "colorPickerItem",
    "rangeSlider": "rangeSliderItem",
    "slider": "sliderItem",
    "item": "customItem",
    "info": "infoItem",
    "expandableGroup": "expandableGroupItem",
    "footer": "footerItem",
    "loading": "loadingItem",
    "subHeader": "subHeaderItem",
    "searchBar": "searchBarItem",
    "fullScreenSearch": "fullScreenSearchItem",
    "items": "itemsSample"
}

def update_home_version():
    if not os.path.exists(HOME_FILE):
        print(f"Error: {HOME_FILE} not found.")
        return

    target_version = os.environ.get("TARGET_VERSION")
    if not target_version:
        print("Warning: TARGET_VERSION env var not set, skipping Home.md update.")
        return

    with open(HOME_FILE, 'r') as f:
        content = f.read()

    pattern = r'implementation\("fr\.paeelluu:compose-settings-ui:[^"]*"\)'
    replacement = f'implementation("fr.paeelluu:compose-settings-ui:{target_version}")'
    new_content = re.sub(pattern, replacement, content)

    if new_content != content:
        with open(HOME_FILE, 'w') as f:
            f.write(new_content)
        print(f"Updated Home.md with version {target_version}")

def parse_kdoc():
    if not os.path.exists(SOURCE_FILE):
        print(f"Error: {SOURCE_FILE} not found.")
        return {}

    with open(SOURCE_FILE, 'r') as f:
        content = f.read()

    kdoc_pattern = re.compile(r'/\*\*(.*?)\*/\s+(?:public|private|internal|protected)?\s+(?:inline|tailrec|external|suspend|abstract|override|open|final)?\s*fun\s+(?:<.*?>\s+)?(\w+)', re.DOTALL)

    components = {}
    for kdoc, fun_name in kdoc_pattern.findall(content):
        lines = [line.strip().lstrip('*').strip() for line in kdoc.strip().split('\n')]

        description = []
        params = []
        category = "Other"

        for line in lines:
            if line.startswith("@param"):
                params.append(line.replace("@param", "").strip())
            elif line.startswith("@category"):
                category = line.replace("@category", "").strip()
            elif not line.startswith("@"):
                if line:
                    description.append(line)

        components[fun_name] = {
            "description": "\n".join(description),
            "params": params,
            "category": category
        }

    return components

def update_components_docs():
    kdoc_data = parse_kdoc()
    if not kdoc_data:
        return

    wiki_images_path = os.path.join(WIKI_PATH, "images")
    if not os.path.exists(wiki_images_path):
        os.makedirs(wiki_images_path)

    unique_id = int(time.time())
    prefix = "fr.paeelluu.composeunifiedsettingsui_SettingsItemScreenshots_"

    categories = {}
    for name, data in kdoc_data.items():
        cat = data["category"]
        if cat not in categories:
            categories[cat] = []
        categories[cat].append((name, data))

    new_content = "# Components Catalog\n\nAll components are available within the `SettingsSectionScope`.\n\n"

    ordered_categories = ["Basic Actions", "Toggles", "Input", "Selection", "Sliders", "Pickers", "Structure & Layout"]
    for cat in categories.keys():
        if cat not in ordered_categories:
            ordered_categories.append(cat)

    for cat in ordered_categories:
        if cat not in categories:
            continue

        new_content += f"## {cat}\n\n"

        for name, data in categories[cat]:
            new_content += f"### {name}\n"

            snapshot_suffix = COMPONENT_MAPPING.get(name)
            if snapshot_suffix:
                # Add unique_id to filename to bust cache
                snapshot_file = f"{prefix}{snapshot_suffix}_{unique_id}.png"
                src_path = os.path.join(SNAPSHOTS_PATH, f"{prefix}{snapshot_suffix}.png")
                dst_path = os.path.join(wiki_images_path, snapshot_file)

                if os.path.exists(src_path):
                    shutil.copy(src_path, dst_path)
                    new_content += f"![Preview](images/{snapshot_file})\n\n"

            new_content += f"{data['description']}\n\n"

            if data['params']:
                new_content += "**Parameters:**\n\n"
                new_content += "| Name | Description |\n"
                new_content += "| --- | --- |\n"
                for param in data['params']:
                    parts = param.split(None, 1)
                    if len(parts) == 2:
                        new_content += f"| `{parts[0]}` | {parts[1]} |\n"
                    else:
                        new_content += f"| `{parts[0]}` | - |\n"
                new_content += "\n"

        new_content += "---\n\n"

    with open(COMPONENTS_FILE, 'w') as f:
        f.write(new_content)
    print("Updated Components.md from KDoc with unique image IDs.")

if __name__ == "__main__":
    update_home_version()
    update_components_docs()