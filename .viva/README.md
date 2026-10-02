# Defend your submission

Passing the tests is not the whole assignment. Before your submission counts,
you have to talk through what you built.

## Procedure

1. Push your implementation. The viva asks about one specific commit, so
   whatever you have pushed is what you will be asked about: do it once your
   work is finished, not before.
2. Open <https://wiser-sp4.interactions.ics.unisg.ch> and sign in with
   GitHub. Pick this repository and the commit you just pushed.
3. Answer the questions. They are about *your* code: the choices you made,
   and what happens in cases you may not have considered.
4. When you finish you are given a record of the conversation. Commit it into
   `.viva/` and push. **Your submission is not complete until that record is
   in your repository**, because it is the only evidence the viva happened.

## Grading

**No mark is produced here**, and no machine scores your responses. The
transcript records the conversation for later review.

There are no right answers to prepare. "I don't know" and "I hadn't thought
about that" are perfectly good things to say.

## Explain your code

That you can explain the decisions in the code you submitted. If you used an AI
assistant to write part of it, that is fine and it is not what this is checking
for, but you should be able to say why the code is the way it is.

## If something goes wrong

If the viva will not load, the questions make no sense, or you finish without
being given a record to commit, contact the teaching staff.

Do not edit `.viva/token.json`, `.viva/turns.json` or `.viva/transcript.md`.
`turns.json` is the record of what was asked and answered. Marking re-reads it,
re-computes its fingerprint, and compares that to the one signed into your
token, so changing a single word of a question or an answer makes the
fingerprints disagree and the record stops counting. `transcript.md` is the
same conversation rendered for a human to read.