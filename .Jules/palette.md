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

## 2025-10-21 - Touch Target Size for Clickable Text
**Learning:** Found an inline clickable text link (`autoLink="web"`) without padding, resulting in a touch target size smaller than recommended accessibility standards, making it hard to tap on mobile.
**Action:** Always add at least `8dp` padding (or more to reach 48x48dp overall size) to interactive text views like links or actionable labels to ensure a sufficient touch target.

## 2025-10-21 - TableLayout Label Alignment
**Learning:** Label `TextView` elements in `TableRow` containers default to top alignment, appearing misaligned compared to taller sibling controls like Spinners or padded inputs.
**Action:** Explicitly set `android:layout_gravity="center_vertical"` on label elements within `TableLayout` to ensure visual harmony with their corresponding controls.
## 2025-10-21 - Visual Alignment in TableRows
**Learning:** In `TableLayout`, sibling controls (like labels and spinners) in a `TableRow` might not vertically align correctly if they have different heights (e.g., standard text views vs padded spinners).
**Action:** Always verify alignment of rows and explicitly apply `android:layout_gravity="center_vertical"` to label elements to ensure they align properly with taller input controls to prevent jagged UI flow.
## 2025-10-21 - Touch Targets on Interactive Links
**Learning:** Found an `autoLink` TextView containing a repository URL that lacked a minimum touch target area, making it difficult for mobile users to tap accurately. Additionally, its adjacent descriptive label was not programmatically associated with it for screen readers.
**Action:** Always add `android:padding="8dp"` to interactive link TextViews to ensure a minimum touch target (e.g., 48dp minimum recommendation). Additionally, ensure adjacent labels are associated using `android:labelFor` to provide context for screen readers.
## 2024-05-15 - Android TableLayout Alignment and Link Accessibility
**Learning:** In Android `TableLayout`s, label `TextView`s often mismatch in height with adjacent interactive elements (like `Spinner`s or text fields with padding). Additionally, `autoLink="web"` elements need minimum touch targets and explicit association with their labels.
**Action:** Always add `android:layout_gravity="center_vertical"` to label `TextView`s in `TableLayout`s for consistent visual alignment. Ensure clickable links (`autoLink="web"`) have adequate padding (e.g., `8dp`) for touch targets, and use `android:labelFor` on the corresponding label to associate it for screen readers.

## 2025-10-21 - Localization of Interactive Feedback
**Learning:** Found a hardcoded string `"Copied to clipboard"` used in a Toast message for interactive feedback. Hardcoded strings cannot be localized and are not accessible to non-English screen readers.
**Action:** Always use string resources (`R.string.*`) for all user-facing text, including Toast messages and Snackbars, to ensure proper localization and accessibility support.

## 2025-10-21 - Custom Button Drawable Focus States
**Learning:** Custom button drawables often lack focus states, making them inaccessible to keyboard and D-pad users who rely on visual indicators.
**Action:** Always include `<item android:state_focused="true">` when creating custom state selectors for interactive elements to ensure accessibility.

## 2026-03-15 - Interactive Tooltips require focus states and minimum touch targets
**Learning:** TextViews acting as interactive tooltips (using `android:tooltipText` and `android:focusable="true"`) are difficult for users to access or recognize without a focus state or sufficient touch size.
**Action:** When adding or modifying interactive tooltip views, ensure they have `android:clickable="true"`, `android:background="?attr/selectableItemBackground"`, and `android:minHeight="48dp"` for keyboard navigation visibility and touch accessibility.
