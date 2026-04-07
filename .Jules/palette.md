## 2025-10-21 - Maintain proper column structure in TableLayout
**Learning:** In a `TableLayout`, if multiple interactive elements (like switches) are placed sequentially in a single `TableRow` without a spanning container, they might break the expected column structure. Similarly, full-width decorative elements (like dividers) might clip or restrict to a single column.
**Action:** Always group related interactive sibling elements inside a spanning parent container (e.g., `LinearLayout` with `android:layout_span`) and apply `android:layout_span` to full-width decorative elements to maintain structural integrity and layout harmony in `TableLayout`.

## 2026-04-02 - Text Scaling on Interactive Elements
**Learning:** Hardcoding `android:layout_height="48dp"` on text-based interactive elements like `Switch` or `Spinner` causes vertical text clipping when users enable large font sizes in their accessibility settings, violating WCAG 1.4.4 (Resize text).
**Action:** Always prefer `android:layout_height="wrap_content"` paired with `android:minHeight="48dp"`. This ensures text can freely expand vertically when scaled while maintaining the required 48dp minimum accessible touch target size.

## 2025-10-21 - Custom Action Descriptions for TalkBack
**Learning:** Generic TalkBack click announcements (e.g., "Double tap to activate") on custom clickable views (like a TextView used to copy an IP address) lack context for visually impaired users.
**Action:** Use `ViewCompat.setAccessibilityDelegate` to override the generic click action with a descriptive custom `AccessibilityActionCompat` (e.g., "Double tap to copy IP to clipboard"), giving clear interaction context.

## 2025-10-21 - Touch Target Spacing for Sibling Controls
**Learning:** Horizontally stacked interactive sibling controls (e.g., Switches inside a LinearLayout) lack inherent spacing in Android layouts, which can lead to overlapping or excessively close touch targets, increasing the risk of accidental mis-taps.
**Action:** Always apply explicit spacing, such as `android:layout_marginEnd="16dp"`, to interactive sibling elements to ensure adequate physical separation between touch targets, particularly on touch-first interfaces.