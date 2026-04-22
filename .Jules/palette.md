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

## 2025-03-16 - Make Tooltips Accessible Touch Targets
**Learning:** When making tooltip text views accessible touch targets (adding minHeight, clickable, and selectableItemBackground), parent container heights (like TableRow or LinearLayout) often need to be changed from fixed heights (e.g., 35dp, 48dp) to wrap_content to prevent clipping the expanded accessible touch area.
**Action:** Always inspect the entire parent container chain when increasing minimum touch target sizes to ensure hardcoded layout constraints don't clip the new accessible dimensions.

## 2026-03-17 - Dynamic Content in Fixed Width Containers
**Learning:** Using hardcoded dimensions (e.g., `131dp`) for parent containers like `LinearLayout` inside `TableLayout` can cause adjacent dynamically generated content (like bandwidth feedback `TextViews`) to be horizontally clipped.
**Action:** Always prefer `wrap_content` over fixed widths when parent containers hold dynamically sizing text elements, to ensure the full content is visible and accessible.

## 2025-10-21 - Empty State Placeholders for Dynamic Content
**Learning:** Found dynamic UI elements (frame time and bitrate feedback) that lacked initial text values, resulting in them appearing completely blank/invisible until the data stream started. Additionally, adjacent structural layouts had hardcoded fixed widths (, ) which are susceptible to text clipping during localization or text scaling.
**Action:** Always provide default placeholder string resources (e.g., `-- ms`, `-- kB/sec`) for dynamically updating elements to prevent broken empty states, and prefer  over fixed widths to ensure responsive accessibility.

## 2025-10-21 - Empty State Placeholders for Dynamic Content
**Learning:** Found dynamic UI elements (frame time and bitrate feedback) that lacked initial text values, resulting in them appearing completely blank/invisible until the data stream started. Additionally, adjacent structural layouts had hardcoded fixed widths (`147dp`, `146dp`) which are susceptible to text clipping during localization or text scaling.
**Action:** Always provide default placeholder string resources (e.g., `-- ms`, `-- kB/sec`) for dynamically updating elements to prevent broken empty states, and prefer `wrap_content` over fixed widths to ensure responsive accessibility.

## 2025-10-21 - Clarity in Confirmation Feedback
**Learning:** Found an interactive copy action that displayed an imperative "Copy to clipboard" Toast upon success, creating an ambiguous UX state (did it copy or is it telling me to copy?). Additionally, the permission denied error message was a robotic "Permission request denied".
**Action:** Always use past-tense confirmation messages (e.g., "Copied to clipboard") for success feedback like Toasts or Snackbars to reassure users, and write actionable, explanatory error messages (e.g., "Camera permission is required to stream video") to provide clear context.

## 2025-10-21 - Misleading Interactivity Cues on Informational Elements
**Learning:** Providing `clickable="true"` and `selectableItemBackground` on non-interactive informative `TextView`s (simply to make them focusable for tooltips) creates misleading visual ripples and false "double tap to activate" TalkBack announcements.
**Action:** Only apply clickability and interactive backgrounds to elements that actually trigger an action. For informational tooltips, `focusable="true"` combined with `tooltipText` is sufficient and prevents misleading screen reader feedback.
## 2025-10-21 - Misleading Interactive Attributes on Tooltips
**Learning:** Adding `android:clickable="true"` and `android:background="?attr/selectableItemBackground"` to non-interactive views just to make their tooltips accessible via touch causes screen readers (like TalkBack) to misleadingly announce them as actionable ("double tap to activate").
**Action:** For purely informational views with tooltips, use only `android:focusable="true"` and `android:tooltipText`. This ensures keyboard/D-pad users can focus the view to see the tooltip, while preventing screen readers from falsely advertising an interaction.

## 2025-10-21 - Spinner contentDescription Override
**Learning:** Setting `contentDescription` on an Android `Spinner` causes screen readers (like TalkBack) to announce the description instead of the currently selected value. This hides the actual selected item from visually impaired users.
**Action:** Never set `contentDescription` directly on `Spinner`s unless it dynamically includes the selected value. Instead, use an adjacent `TextView` with `android:labelFor` pointing to the Spinner ID to associate a label while preserving the announcement of the Spinner's selected content.
## 2026-03-23 - Dynamic Accessibility Properties
**Learning:** Added a static `android:contentDescription` to a TextView meant to display dynamic text (frame time/bitrate), which caused TalkBack to permanently read the static description instead of the updated live data.
**Action:** Never set static `android:contentDescription` in XML for elements whose content dynamically updates. The screen reader will prioritize the static XML property over the updated text.

## 2025-10-21 - Maintain proper column structure in TableLayout
**Learning:** In a `TableLayout`, if multiple interactive elements (like switches) are placed sequentially in a single `TableRow` without a spanning container, they might break the expected column structure. Similarly, full-width decorative elements (like dividers) might clip or restrict to a single column.
**Action:** Always group related interactive sibling elements inside a spanning parent container (e.g., `LinearLayout` with `android:layout_span`) and apply `android:layout_span` to full-width decorative elements to maintain structural integrity and layout harmony in `TableLayout`.
## 2026-04-22 - Contextualizing Interactive Dynamic Content
**Learning:** When an interactive element (like a clickable TextView) displays dynamic data, screen readers like TalkBack will often only announce the dynamic data. This leaves users unaware of the secondary action (like 'copy to clipboard') unless they linger for the tooltip. Setting a static `contentDescription` in XML breaks the dynamic announcement entirely.
**Action:** Dynamically set the `contentDescription` in code to include both the dynamic data (e.g., the IP address) and a localized string describing the action (e.g., `getString(R.string.copy_ip_tooltip)`) to guarantee TalkBack users immediately understand both the content and the interactive context.
