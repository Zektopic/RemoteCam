## 2025-10-21 - Maintain proper column structure in TableLayout
**Learning:** In a `TableLayout`, if multiple interactive elements (like switches) are placed sequentially in a single `TableRow` without a spanning container, they might break the expected column structure. Similarly, full-width decorative elements (like dividers) might clip or restrict to a single column.
**Action:** Always group related interactive sibling elements inside a spanning parent container (e.g., `LinearLayout` with `android:layout_span`) and apply `android:layout_span` to full-width decorative elements to maintain structural integrity and layout harmony in `TableLayout`.

## 2026-04-02 - Text Scaling on Interactive Elements
**Learning:** Hardcoding `android:layout_height="48dp"` on text-based interactive elements like `Switch` or `Spinner` causes vertical text clipping when users enable large font sizes in their accessibility settings, violating WCAG 1.4.4 (Resize text).
**Action:** Always prefer `android:layout_height="wrap_content"` paired with `android:minHeight="48dp"`. This ensures text can freely expand vertically when scaled while maintaining the required 48dp minimum accessible touch target size.

## 2025-10-21 - Custom Action Descriptions for TalkBack
**Learning:** Generic TalkBack click announcements (e.g., "Double tap to activate") on custom clickable views (like a TextView used to copy an IP address) lack context for visually impaired users.
**Action:** Use `ViewCompat.setAccessibilityDelegate` to override the generic click action with a descriptive custom `AccessibilityActionCompat` (e.g., "Double tap to copy IP to clipboard"), giving clear interaction context.

## 2025-10-21 - Horizontal Spacing for Interactive Siblings
**Learning:** Placing interactive sibling elements (like multiple Switches or a Spinner next to a TextView) sequentially in a horizontal `LinearLayout` without explicit margins creates a cluttered UI and violates minimum touch target spacing guidelines, leading to potential mis-taps.
**Action:** Always add explicit spacing, such as `android:layout_marginEnd="16dp"`, to the inner sibling elements when grouping horizontally stacked interactive controls to ensure clear visual separation and accessible touch targets.

## 2025-10-21 - Touch Target Spacing for Sibling Controls
**Learning:** Horizontally stacked interactive sibling controls (e.g., Switches inside a LinearLayout) lack inherent spacing in Android layouts, which can lead to overlapping or excessively close touch targets, increasing the risk of accidental mis-taps.
**Action:** Always apply explicit spacing, such as `android:layout_marginEnd="16dp"`, to interactive sibling elements to ensure adequate physical separation between touch targets, particularly on touch-first interfaces.

## 2025-10-21 - Accessible autoLink TextViews
**Learning:** `TextView`s with `android:autoLink="web"` make text links interactive but may not provide proper context or affordances to screen readers on their own. They can act as opaque clickable blocks without a clear spoken description.
**Action:** Always add `android:clickable="true"` and an informative `android:contentDescription` (e.g., "Open GitHub repository") to `autoLink` `TextView`s to ensure visually impaired users understand the link's destination and purpose.

## 2025-10-21 - Universal Interaction Context via Tooltips
**Learning:** While `contentDescription` provides crucial interaction context for TalkBack users on custom link elements (like `autoLink` TextViews), sighted users navigating via keyboard or mouse lack this context unless a visual tooltip is also provided.
**Action:** Always pair `android:contentDescription` with an equivalent `android:tooltipText` on interactive elements that lack clear visual labels to ensure universal access to interaction context across all input modalities.

## 2026-04-11 - Prevent unreadable hardcoded text sizing
**Learning:** Hardcoding `android:textSize="12sp"` directly on Android TextView elements creates an accessibility barrier for users with poor vision and ignores system-level font scaling preferences, particularly for dynamic data fields like bitrate readouts.
**Action:** Remove overly restrictive `android:textSize` attributes from informational UI elements, allowing them to inherit accessible default text sizes from the application theme.

## 2026-04-13 - Stop Button Accessibility
**Learning:** Destructive action buttons like 'STOP' might only have a simple text label, which lacks detail for screen reader users and mouse hover contexts.
**Action:** Add `android:contentDescription` and `android:tooltipText` with explicit consequences (e.g., 'Stop camera server') to brief action buttons.

## 2025-10-21 - Accessible Tooltips for Destructive Actions
**Learning:** Action buttons like "STOP" with brief labels may not convey their full impact (e.g., stopping the stream AND exiting the server) to all users, particularly those relying on screen readers or keyboard navigation.
**Action:** Enhance brief or destructive action buttons by providing both `android:contentDescription` and `android:tooltipText` with explicit descriptions of the outcome, ensuring clarity for assistive technologies and mouse/keyboard hover states.

## 2025-10-21 - Destructive Action Context for Screen Readers
**Learning:** Brief or visually compact buttons for destructive actions (like a "STOP" button that kills a server and closes the app) lack sufficient context for visually impaired users or keyboard navigators, making accidental activation a high risk.
**Action:** Enhance brief or destructive action buttons with explicit `android:contentDescription` and `android:tooltipText` that fully describe the action's outcome (e.g., "Stop camera server") to ensure users are fully aware of the consequences before interacting.

## 2026-04-14 - Keyboard focus indicators on focusable tooltips
**Learning:** When adding `android:focusable="true"` to informational views (like TextViews) solely to enable keyboard/D-pad access to `android:tooltipText`, the view does not automatically receive a visual focus state, violating WCAG 2.4.7 Focus Visible.
**Action:** Always pair `android:focusable="true"` with a visual focus indicator (e.g., `android:background="?attr/selectableItemBackground"`) on informational views that present tooltips to ensure keyboard users can track their location on screen.
