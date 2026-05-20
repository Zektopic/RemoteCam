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

## 2026-04-14 - Descriptive Labels for Brief/Destructive Actions
**Learning:** Brief or destructive action buttons (like 'STOP') may lack context for screen reader users and sighted users hovering with a mouse, especially when the visual label is short.
**Action:** Enhance brief or destructive buttons with `android:contentDescription` and `android:tooltipText` that explicitly describe their full outcome (e.g., 'Stop camera server') to provide clear context across input modalities.
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

## 2025-10-21 - Accessible Formatting for Numerical Spinners
**Learning:** Displaying raw numbers (e.g., "80") in a `Spinner` lacks context for all users, particularly screen reader users who might just hear "80" without knowing the unit or what it represents.
**Action:** Always format numerical `Spinner` items with appropriate contextual units (e.g., using a string resource like `"%1$d%%"` to display "80%") to ensure clear visual and spoken context.
## 2023-10-25 - Custom Accessibility Actions for AutoLink TextViews
**Learning:** Android's default TalkBack announcement ("Double tap to activate") is often insufficient for TextViews utilizing `android:autoLink="web"` (or custom link handlers like copying to clipboard). While these views correctly receive focus, their purpose remains ambiguous to screen reader users without contextual descriptions.
**Action:** Always assign a custom `AccessibilityDelegateCompat` with an overridden `ACTION_CLICK` containing a descriptive label (e.g., "Double tap to open GitHub repository" or "Double tap to copy IP to clipboard") to auto-linked or interactive informational TextViews to ensure their interaction context is explicitly clear.

## 2025-10-21 - Localized String Formatting for Dynamic Text
**Learning:** Using Kotlin string templates (e.g., `"$localIp:8080/cam.mjpeg"`) for UI text updates circumvents Android's localization system, creating potential accessibility and translation issues for screen readers in different locales.
**Action:** Always use Android string resources with format placeholders (e.g., `getString(R.string.ip_format, localIp)`) for dynamically generated UI text to guarantee proper localization and accessibility context.

## 2024-04-20 - Source Repo Clickable Link
**Learning:** The URL representing the source repository was using `android:autoLink="web"` with an overridden accessibility delegate that required an explicit `OnClickListener` in code, but no such listener was set, so users could not click the link and TalkBack's double-tap behavior would fail.
**Action:** Always ensure that when overriding accessibility actions for autoLink text views, an explicit `OnClickListener` (e.g. `startActivity(Intent(Intent.ACTION_VIEW...))`) is provided in the code to handle the default behavior that autoLink normally handles, otherwise the link becomes unclickable.
## 2025-10-21 - Link Actions and Intent Handling
**Learning:** Implementing a custom TalkBack action (AccessibilityDelegateCompat with ACTION_CLICK) on a generic TextView to simulate link interaction does not automatically make the link navigable. It only provides spoken feedback. Sighted users are left without functionality, and it creates a crash vector if an implicit intent is sent without proper handling.
**Action:** Avoid masking functionality gaps with accessibility labels. If a view needs to act as a link, ensure it has a functional setOnClickListener that safely handles intents (e.g., catching ActivityNotFoundException), and provide visual affordances (like touch ripples or link colors) to make the interactivity clear to all users.

## 2026-04-22 - Intent Crash Prevention on Custom Links
**Learning:** Implementing a custom link action via a generic `TextView` using `startActivity(Intent(Intent.ACTION_VIEW, ...))` can crash the app with an `ActivityNotFoundException` if the user's device lacks a registered application (like a web browser) to handle the scheme. Furthermore, if a hardcoded error message is used, it hurts localization efforts and accessibility context.
**Action:** Always wrap implicit intent calls (e.g., opening a URL) in a `try-catch` block catching `ActivityNotFoundException`. Provide user feedback using a Toast or Snackbar that utilizes a localized string resource (e.g., `getString(R.string.error_no_browser)`) explaining the failure actionably, rather than hardcoding the string.

## 2026-04-23 - Switch Context Tooltips
**Learning:** Toggle switches (`Switch`) often use very brief or acronym-based text labels (e.g., "RTSP") to save space. While screen readers will announce the label and the state, sighted keyboard/mouse users or users who aren't familiar with the acronym might lack context without further explanation.
**Action:** Enhance brief or ambiguous toggle switches by adding an explicit `android:tooltipText` (e.g., "Toggle RTSP video stream") to provide clear interaction context on hover or long-press.

## 2024-04-26 - [Dynamic contentDescription for secondary action elements]
**Learning:** When an interactive UI element displays dynamic text but performs a secondary action (e.g., a TextView showing an IP address that copies to clipboard on click), relying only on visual text or tooltip string resources might cause screen readers to miss the action context or underlying data.
**Action:** Dynamically set its `contentDescription` in code to include both the dynamic data and a localized string describing the action (e.g., combining the IP string and `R.string.copy_ip_tooltip`) to ensure screen readers announce the full action context without losing the underlying data.

## 2026-04-24 - Consistent Sibling Spacing
**Learning:** The last horizontally stacked interactive sibling control (`switchRTSP`) was missing `android:layout_marginEnd="16dp"`, leading to inconsistent spacing and potentially affecting usability on narrow screens where elements might wrap or touch the screen edge.
**Action:** Always ensure consistent trailing margins on all sibling elements in a horizontal layout group to guarantee uniform touch targets and visual padding.

## 2026-05-15 - Universal Interaction Context for Spinners
**Learning:** Standard Android `Spinner` elements correctly associate with visual labels for TalkBack via `android:labelFor` on adjacent `TextView`s, but they inherently lack hover context for sighted users navigating via mouse or keyboard unless explicitly provided.
**Action:** Always enhance `Spinner` elements with an explicit `android:tooltipText` pointing to a descriptive localized string resource (e.g., "Select compression quality") to provide clear interaction context for hover and long-press users, ensuring universal accessibility across all input modalities.

## 2026-05-18 - Mouse Hover States for Custom Selectors
**Learning:** Custom drawable selectors (like `btn_ios_background.xml`) on Android often define `state_pressed` and `state_focused` but omit `state_hovered`. This strips visual feedback for users navigating with pointer devices (mice, trackpads) on environments like Chromebooks or Samsung DeX, degrading the user experience compared to native components.
**Action:** Always include `android:state_hovered="true"` alongside focus and pressed states in custom interactive background selectors to ensure universal visual feedback across all input methods.

## 2026-05-19 - Redundant Accessibility Announcements for Links
**Learning:** Adding a static `android:contentDescription` to an `android:autoLink` `TextView` overrides TalkBack's ability to read the underlying text (the URL itself). When this is combined with a custom `AccessibilityDelegateCompat` that provides a descriptive action context (e.g., "Double tap to open GitHub repository"), it causes redundant and potentially confusing announcements (e.g., "Open GitHub repository. Double tap to open GitHub repository") while hiding the actual URL from the user.
**Action:** Do not set a static `android:contentDescription` on auto-linked `TextView`s. Allow TalkBack to read the underlying URL naturally, and rely on the custom `AccessibilityDelegateCompat` to provide the descriptive interaction context for the link.

## 2026-05-20 - Redundant Custom Clipboard Toasts
**Learning:** On Android 13 (API 33) and above, the system automatically displays a standard visual confirmation UI when text is copied to the clipboard. Showing a custom `Toast` message for this action results in redundant, stacked visual feedback that clutters the UI.
**Action:** Always conditionally check the API level (e.g., `Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2`) before showing custom clipboard confirmation `Toast` messages to ensure a clean experience on modern Android devices.

## 2026-05-21 - Visual Styling for Destructive Actions
**Learning:** While destructive action buttons (like stopping a server and closing an app) might have accessible descriptions and tooltips, using the primary app color (e.g., standard blue) for them fails to visually communicate their severity to sighted users, increasing the risk of accidental activation.
**Action:** Always visually differentiate destructive action buttons from primary actions by applying a distinct, culturally understood destructive color (e.g., red) across all interactive states (normal, pressed, hovered, focused) using a custom style and drawable selector.
## 2024-05-07 - App Title Spacing
**Learning:** Structural headings (like app titles) placed adjacent to interactive sibling controls within flexible layouts (like TableRow) can cause visual crowding and touch target overlap on narrow screens.
**Action:** Always apply an explicit `marginEnd` (e.g., `16dp`) to headings when horizontally adjacent to interactive elements to guarantee minimum spacing.

## 2026-05-22 - Graceful Exit on Core Permission Denial
**Learning:** When an app relies entirely on a core permission (like the camera for a streaming app) and requests it from a headless routing fragment (like `PermissionsFragment`), simply displaying a "permission denied" Toast upon refusal leaves the user stranded on a blank, un-interactive screen. This creates a broken app experience where the user is trapped without any path forward.
**Action:** Always provide a graceful exit (e.g., `activity?.finish()`) immediately after notifying the user via Toast when a mandatory permission is denied in a headless or startup fragment, preventing the user from being stranded on an empty state.

## 2026-05-23 - TableRow Label Spacing
**Learning:** Structural text labels (e.g., `TextView` acting as `labelFor` a `Spinner`) in the first column of a `TableLayout` can crowd adjacent interactive elements in the second column if they lack explicit margins, potentially causing touch target overlap on narrow screens. Furthermore, setting `android:layout_width="match_parent"` on a `wrap_content` TableRow item negates its margin behaviour and causes unnecessary scaling.
**Action:** Always apply an explicit `android:layout_marginEnd="16dp"` and `android:layout_width="wrap_content"` to label views in the first column of a flex or table layout to ensure predictable sizing and guarantee sufficient visual padding and touch target separation from sibling controls.

## 2026-05-24 - Semantic Roles for Custom Clickable Views
**Learning:** Using `AccessibilityDelegateCompat` to add an `ACTION_CLICK` with a descriptive label to a custom view (like a `TextView` acting as a link or copy button) provides the interaction context, but screen readers may still announce the view's base class (e.g., just reading the text without indicating it's a "Button" or interactive element type).
**Action:** When creating custom clickable views with overridden accessibility delegates, explicitly assign a semantic role by setting `info.className` (e.g., `info.className = android.widget.Button::class.java.name`) in `onInitializeAccessibilityNodeInfo` so screen readers announce its correct interactive nature.
## 2026-05-11 - Semantic Roles for Custom Clickable Views
**Learning:** When creating custom clickable views (like a `TextView` acting as a button or link) with overridden accessibility delegates, screen readers may not announce their interactive nature correctly, leaving users unaware that the element is clickable.
**Action:** Explicitly assign a semantic role by setting `info.className` (e.g., `info.className = android.widget.Button::class.java.name`) in `onInitializeAccessibilityNodeInfo` to ensure screen readers announce its correct interactive nature.
## 2026-05-24 - Semantic Roles for Custom Interactive Views
**Learning:** When creating custom clickable views (like a `TextView` acting as a button or link) and assigning them a custom `AccessibilityDelegateCompat` to define their action context, they will still be announced by screen readers according to their base class (e.g., as text rather than an actionable control). This lacks clear interaction affordances for the user.
**Action:** Always explicitly assign a semantic role by setting `info.className` (e.g., `info.className = android.widget.Button::class.java.name`) within `onInitializeAccessibilityNodeInfo` to ensure screen readers accurately announce the interactive nature of the custom component.
## 2026-05-25 - External Link Visual Affordance
**Learning:** Interactive text views that navigate the user outside of the app (e.g., opening a GitHub repository URL in an external web browser) can cause disorientation if the user expects an in-app transition. While screen readers might announce the interaction context via `AccessibilityDelegateCompat`, sighted users lack a visual affordance.
**Action:** Always provide a clear visual indicator, such as an "open in new" external link icon (e.g., using `android:drawableEnd`), on interactive elements that trigger external applications or browsers, ensuring users are not surprised by the context switch.

## 2026-05-26 - Enable RTL Support
**Learning:** Android apps must explicitly opt-in to Right-to-Left (RTL) layout support by adding `android:supportsRtl="true"` to the `<application>` tag in `AndroidManifest.xml`. Even if RTL-aware directional attributes (like `layout_marginEnd` or `paddingStart`) are used in XML layouts, the OS will ignore them and fall back to LTR rendering if this manifest flag is missing, breaking the layout for users of RTL languages like Arabic or Hebrew.
**Action:** Always ensure `android:supportsRtl="true"` is declared in the manifest when using start/end directional attributes to guarantee proper RTL rendering.

## 2026-05-27 - Vertical Alignment for Enlarged Touch Targets
**Learning:** When using `android:minHeight="48dp"` (or similar attributes) to increase the physical touch target size of text-based interactive elements (like `TextView`s acting as buttons or links), the inner text may float to the top of the bounded area, causing visual misalignment with vertically centered adjacent labels or sibling controls.
**Action:** Always apply `android:gravity="center_vertical"` (or `gravity="center"`) to `TextView`s that use `minHeight` for touch target expansion to ensure the text content remains visually aligned within the expanded container.

## 2026-05-28 - Initial Content Descriptions for Dynamic TextViews
**Learning:** Dynamic text views that use placeholder text for their initial state (e.g., "-- ms") present a poor accessibility experience, as screen readers will literally announce the confusing placeholder punctuation (e.g., "dash dash ms"). While these views receive rich content descriptions upon dynamic updates, they remain inaccessible during the initial waiting period.
**Action:** Always programmatically set a descriptive `contentDescription` (e.g., "Unknown frame latency") during view initialization for dynamic text elements to ensure their placeholder state is clearly communicated to screen reader users until the first data update occurs.

## 2026-05-29 - Typographic Affordances for Dimensions
**Learning:** Using the default `Size.toString()` output (which generates e.g., "1920x1080" with a lowercase 'x') or hardcoding lowercase letters for dimensions degrades screen reader accessibility, as TalkBack will literally read "1920 eks 1080". Additionally, the lack of proper spacing reduces legibility for sighted users.
**Action:** Always use a string resource format (e.g., `%1$d × %2$d`) featuring the proper typographic multiplication sign (`×`, U+00D7) and explicit spaces when displaying dimensional values. This ensures screen readers correctly pronounce "1920 by 1080" while providing superior typographic readability for all users.
