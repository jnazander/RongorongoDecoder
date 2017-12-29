# RongorongoDecoder
A Java program that attempts to decode the Rongorongo texts using various brute-force and dictionary methods.

https://en.wikipedia.org/wiki/Rongorongo
https://en.wikipedia.org/wiki/Decipherment_of_rongorongo

This decode method is based on the probability that the as-of-yet undecyphered Rongorongo texts encode the ancient Rapanui language. Since we do have a dictionary of ancient (pre-european contact) Rapanui language, then in theory we could use it to try and apply various rapanui->rongorongo glyph mappings and see if any mapping reveals meaningful text.

In particular, we should take long Rapanui dictionary words, and assuming that same sounds within these words are encoded in same Rongorongo glyphs, we should find glyph sequences that have the same pattern of repeating glyphs as the sounds within Rapanui words. After finding at least one match, we can save these glyph-sound mappings, then use them to find other known dictionary words, and repeat until all glyphs have been mapped to a sound.

One big obstacle to this approach is that we don't know what kind of writing system does Rongorongo represent. It could be one or a combination of the following:

- Logographic or ideographic writing system (like the Chinese Hanzi)
- Alphabetic (like the Latin alphabet)
- Syllabic (like the Japanese Kana)
- Alphasyllabic or Abugida (like Devanagari)
- A combination of any of the above

At first, I started this project based on the assumption that it may be an Alphabetic system, where each Rongorongo glyph represents a letter (either a Consonant or a Vowel). The view was postulated by Konstantin Pozdniakov in his 2007 paper (titled Rapanui Writing and the Rapanui Language: Preliminary Results of a Statistical Analysis). However, after not having any luck with various combinations, I changed the program to treat Rongorongo as a Syllabic text, and later as Alphasyllabic text. So far, no meaningful text patterns have come up.

The Rapanui dictionary used here is taken mainly from http://kohaumotu.org/Rongorongo/Dictionary/dictionary_complete.html, as well as from various other texts and poems at http://kohaumotu.org.
