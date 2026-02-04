# Palette's Journal

## 2025-10-21 - Legacy TableLayout & Accessibility
**Learning:** This app uses `TableLayout` for general UI structure, not tabular data. This disconnects labels (TextViews) from their controls (Spinners) in the accessibility tree, as they are just siblings in a TableRow.
**Action:** When encountering TableLayout-based forms, always verify that inputs have explicit `contentDescription` or `labelFor` attributes, as the visual proximity in a TableRow is not semantically sufficient for screen readers.

## 2025-10-21 - Discoverability of Interactive Text
**Learning:** The IP address display was interactive (tap to copy) but lacked visual cues, making the feature undiscoverable.
**Action:** Always add `android:background="?attr/selectableItemBackground"` and `android:tooltipText` to interactive TextViews to provide visual affordance and hint at functionality.
