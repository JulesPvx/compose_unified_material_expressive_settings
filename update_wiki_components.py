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
#  /home/paeelluu/StudioProjects/compose_unified_material_expressive_settings/update_wiki_components.py
#
#  This file is part of the Compose Unified Material Expressive Settings Library.
#  Licensed under the Proprietary License.
#  All rights reserved.

import os
import re

WIKI_PATH = "wiki"
COMPONENTS_FILE = os.path.join(WIKI_PATH, "Components.md")
SNAPSHOTS_PATH = "../sample/src/test/snapshots/images" # Relative to wiki clone

# Mapping between Component Header in Markdown and the Snapshot file name suffix
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
    "searchBar": "searchBarItem"
}

def update_components_docs():
    if not os.path.exists(COMPONENTS_FILE):
        print(f"Error: {COMPONENTS_FILE} not found.")
        return

    with open(COMPONENTS_FILE, 'r') as f:
        content = f.read()

    # Base URL for wiki images is just the filename if uploaded to the wiki repo
    # But for simplicity, we will copy images to the wiki repo and reference them locally
    
    # Ensure images directory exists in wiki
    wiki_images_path = os.path.join(WIKI_PATH, "images")
    if not os.path.exists(wiki_images_path):
        os.makedirs(wiki_images_path)

    # Copy snapshots to wiki images directory
    # Snapshot format: fr.paeelluu.composeunifiedsettingsui_SettingsItemScreenshots_actionItem.png
    prefix = "fr.paeelluu.composeunifiedsettingsui_SettingsItemScreenshots_"
    
    for component, snapshot_suffix in COMPONENT_MAPPING.items():
        snapshot_file = f"{prefix}{snapshot_suffix}.png"
        src_path = os.path.join(SNAPSHOTS_PATH, snapshot_file)
        dst_path = os.path.join(wiki_images_path, snapshot_file)
        
        if os.path.exists(src_path):
            import shutil
            shutil.copy(src_path, dst_path)
            print(f"Copied {snapshot_file} to wiki.")
            
            # Insert image after the header in Markdown
            # Search for ### component and insert ![Preview](images/snapshot_file) below it
            header_pattern = rf"(### {component}\n.*?\n)"
            image_md = f"\n![Preview](images/{snapshot_file})\n"
            
            # Avoid duplicate inserts
            if image_md not in content:
                content = re.sub(header_pattern, r"\1" + image_md, content)
        else:
            print(f"Warning: Snapshot for {component} not found at {src_path}")

    with open(COMPONENTS_FILE, 'w') as f:
        f.write(content)

if __name__ == "__main__":
    update_components_docs()
