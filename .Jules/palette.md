# Palette's Journal

## 2025-10-21 - Legacy TableLayout & Accessibility
**Learning:** This app uses `TableLayout` for general UI structure, not tabular data. This disconnects labels (TextViews) from their controls (Spinners) in the accessibility tree, as they are just siblings in a TableRow.
**Action:** When encountering TableLayout-based forms, always verify that inputs have explicit `contentDescription` or `labelFor` attributes, as the visual proximity in a TableRow is not semantically sufficient for screen readers.

## 2025-10-21 - Discoverability of Interactive Text
**Learning:** The IP address display was interactive (tap to copy) but lacked visual cues, making the feature undiscoverable.
**Action:** Always add `android:background="?attr/selectableItemBackground"` and `android:tooltipText` to interactive TextViews to provide visual affordance and hint at functionality.
## 2025-10-21 - Hidden Interactions
**Learning:** Found critical actions (copy IP) implemented on plain TextViews with no visual cues (ripple, color, tooltip), making features undiscoverable despite existing backing logic.
**Action:** Always check `setOnClickListener` usage in Fragments to find hidden interactive elements and upgrade them with standard accessibility/interaction attributes (`selectableItemBackground`, `focusable`, `tooltipText`).

## 2025-05-18 - Semantic Label Association
**Learning:** While `contentDescription` on inputs helps, explicitly linking `TextView` labels to controls using `android:labelFor` creates a stronger semantic bond for screen readers, allowing "click to focus" behavior on the label.
**Action:** Prefer `android:labelFor` on labels over duplicating text in `contentDescription` on inputs where possible.
## 2025-10-21 - Destructive Actions Without Confirmation
**Learning:** The "STOP" button (killing the server) was a single click away, leading to potential accidental service interruptions.
**Action:** Always implement a confirmation dialog (`AlertDialog`) for actions that stop services or delete data, especially when the control is visually adjacent to non-destructive controls.
## 2025-10-21 - Destructive Action Confirmation
**Learning:** The "STOP" button terminated the camera stream and app immediately without confirmation, posing a risk of accidental interruption.
**Action:** Always wrap destructive actions (like stopping a server or deleting data) in a confirmation dialog (e.g., `AlertDialog`) to prevent data loss or unintended service stoppage.

## 2025-10-22 - Dynamic Technical Metrics
**Learning:** Displaying raw numbers (bitrate/frametime) is meaningless to many users and inaccessible if announced only as numbers by screen readers.
**Action:** Always provide `tooltipText` for technical metrics to explain them, and use dynamic `contentDescription` updates (e.g., "Bitrate: X KB/s") to provide full context to screen reader users instead of just reading the raw text.
