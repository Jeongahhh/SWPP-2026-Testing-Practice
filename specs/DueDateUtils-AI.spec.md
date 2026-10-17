# Spec: `DueDateUtils.kt`

> Post-conditions are the test oracle: write the expected behavior from intent, not from the code.
> Tag each post-condition [Unit] if its test runs on the JVM; otherwise tag it [Integration].
> End each valid case with `Tested by <test>` if an existing test checks it, or `Tip:` describing the test to write.

## `dueDateLabel(dueDate: LocalDate?, today: LocalDate, isCompleted: Boolean): String`

**Intent**: Format a D-day label using whole calendar days, with completion taking precedence.

**Pre-conditions**
- today is an explicit valid LocalDate; dueDate may be null.

**Post-conditions**
- Success cases
  - Given today=2026-10-17, dueDate=null, isCompleted=false, return exactly "". `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_activeWithoutDate_returnsEmpty`
  - Given today=2026-10-17, dueDate=null, isCompleted=true, return exactly "CLEAR". `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_completedWithoutDate_returnsClear`
  - Given today=2026-10-17, dueDate=2026-10-16, isCompleted=true, return exactly "CLEAR". `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_completedPastDate_returnsClear`
  - Given today=2026-10-17, dueDate=2026-10-17, isCompleted=true, return exactly "CLEAR". `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_completedToday_returnsClear`
  - Given today=2026-10-17, dueDate=2026-10-18, isCompleted=true, return exactly "CLEAR". `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_completedFutureDate_returnsClear`
- Error cases
  - None. Null is a valid absence value, not an error. `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_activeWithoutDate_returnsEmpty`
- Edge cases
  - Given today=2026-10-17, dueDate=2026-10-16, isCompleted=false, return exactly "D+1". `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_yesterday_returnsDPlus1`
  - Given today=2026-10-17, dueDate=2026-10-17, isCompleted=false, return exactly "D-Day". `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_today_returnsDDay`
  - Given today=2026-10-17, dueDate=2026-10-18, isCompleted=false, return exactly "D-1". `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_tomorrow_returnsDMinus1`

**Undefined behavior** (optional)
- None for typed inputs.
