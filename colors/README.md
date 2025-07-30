# Module Material You color generator (deprecated)

Pure Kotlin Multiplatform implementation of the Material You color algorithm.

<a href="https://search.maven.org/search?q=dev.opensavvy.material3.utilities"><img src="https://img.shields.io/maven-central/v/dev.opensavvy.material3.utilities/colors.svg?label=Maven%20Central"></a>
<a href="https://opensavvy.dev/open-source/stability.html"><img src="https://badgen.net/static/Stability/archived/purple"></a>
<a href="https://javadoc.io/doc/dev.opensavvy.material3.utilities/colors"><img src="https://badgen.net/static/Other%20versions/javadoc.io/blue"></a>

> This project is archived.
> We do not plan on updating it further.
>
> We recommend you migrate to [MaterialKolor](https://materialkolor.com/).

This is a Kotlin Multiplatform port of [the Java Material You algorithm](https://github.com/material-foundation/material-color-utilities), using Kotlin language features to improve the API.

# Package opensavvy.material3.colors.argb

Efficient ARGB color representation and conversion.

# Package opensavvy.material3.colors.blend

Functions for blending between multiple colors.

# Package opensavvy.material3.colors.contrast

Computing contrast and contrast ratios between multiple colors.

# Package opensavvy.material3.colors.dislike

Avoid universally-disliked colors.

# Package opensavvy.material3.colors.dynamiccolor

Colors and palettes that adapt themselves based on external factors (e.g. the user's preferences).

# Package opensavvy.material3.colors.hct

Cam16 and HCT color spaces.

# Package opensavvy.material3.colors.palettes

Creates different tones of the same color.

# Package opensavvy.material3.colors.quantize

Extract the dominant colors from large amounts of data (e.g. pixels of an image).

See [Quantizer][opensavvy.material3.colors.quantize.Quantizer] and its implementation.

# Package opensavvy.material3.colors.scheme

Built-in dynamic schemes based on a dominant color.

The schemes are available as extensions on the [companion object of BuiltInScheme][opensavvy.material3.colors.scheme.BuiltInScheme.Companion].

# Package opensavvy.material3.colors.score

Rank colors by how suitable they are for a UI theme.

# Package opensavvy.material3.colors.temperature

Compute analogous colors, complementary colors, etc.
