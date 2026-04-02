# EnglishUp UI Design — HTML Reference

> File này chứa HTML design gốc để chuyển đổi sang Kotlin Jetpack Compose.
> 7 Màn hình: Home, Vocabulary, Quiz, Listening, Reading, Progress, Grammar

## Color System
```
--bg: #0A0E1A
--bg2: #111827
--bg3: #1a2235
--surface: #1E2D45
--surface2: #253650
--border: rgba(255,255,255,0.07)
--border2: rgba(255,255,255,0.14)
--text: #F0F4FF
--text2: #8A9BBF
--text3: #4A5A7A
--green: #00E5A0
--green-dim: rgba(0,229,160,0.12)
--red: #FF4D6A
--red-dim: rgba(255,77,106,0.12)
--amber: #FFB547
--amber-dim: rgba(255,181,71,0.12)
--blue: #4D9FFF
--blue-dim: rgba(77,159,255,0.12)
--purple: #A78BFA
--purple-dim: rgba(167,139,250,0.12)
```

## Typography
- Primary: Sora (300, 400, 500, 600, 700)
- Mono: Space Mono (400, 700)

## Navigation
Bottom bar 4 items: Home | Kỹ năng | Tiến độ | Hồ sơ

## Screens
1. **Home** — Greeting, Streak bar, Today goal, Skill cards (2x2), Recent words
2. **Vocabulary** — Tabs (Tất cả/Cần ôn/Đã nhớ/Yêu thích), Flashcard (SRS), Word list
3. **Quiz** — Progress bar, Question card, 4 options (A/B/C/D), Explanation card
4. **Listening** — Audio player + waveform, Dictation input, MCQ questions
5. **Reading** — Passage card (scrollable), MCQ questions with letter badges
6. **Progress** — Stat grid (2x2), Weekly bar chart, Skill breakdown, Daily log
7. **Grammar** — Tabs (TOEIC/IELTS/Cần ôn), Topic list with progress