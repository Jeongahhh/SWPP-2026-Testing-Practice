# Spec: `DueDateUtils.kt`

> Post-conditions are the test oracle: write the expected behavior from intent, not from the code.
> Tag each post-condition [Unit] if its test runs on the JVM; otherwise tag it [Integration].
> End each valid case with `Tested by <test>` if an existing test checks it, or `Tip:` describing the test to write.

## `dueDateLabel(dueDate: LocalDate?, today: LocalDate, isCompleted: Boolean): String`

**Intent**: Format D-N, D-Day, D+N using total calendar days across months, years and leap days. Completed tasks always display CLEAR; active tasks without a due date display an empty string.

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
  - Given today=2026-10-17, dueDate=2026-11-16, isCompleted=false, return exactly "D-30". `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_beforeOneMonth_returnsDMinus30`
  - Given today=2026-10-17, dueDate=2026-11-17, isCompleted=false, return exactly "D-31". `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_exactlyOneMonth_returnsDMinus31`
  - Given today=2026-10-17, dueDate=2026-11-18, isCompleted=false, return exactly "D-32". `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_afterOneMonth_returnsDMinus32`
  - Given today=2026-11-17, dueDate=2026-10-16, isCompleted=false, return exactly "D+32". `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_beforePastMonth_returnsDPlus32`
  - Given today=2026-11-17, dueDate=2026-10-17, isCompleted=false, return exactly "D+31". `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_exactlyPastMonth_returnsDPlus31`
  - Given today=2026-11-17, dueDate=2026-10-18, isCompleted=false, return exactly "D+30". `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_afterPastMonth_returnsDPlus30`
  - Given today=2026-01-01, dueDate=2026-12-31, isCompleted=false, return exactly "D-364". `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_beforeYearEnd_returnsDMinus364`
  - Given today=2026-01-01, dueDate=2027-01-01, isCompleted=false, return exactly "D-365". `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_exactlyOneYear_returnsDMinus365`
  - Given today=2026-01-01, dueDate=2027-01-02, isCompleted=false, return exactly "D-366". `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_afterOneYear_returnsDMinus366`
  - Given today=2027-01-01, dueDate=2026-01-01, isCompleted=false, return exactly "D+365". `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_pastYear_returnsDPlus365`
  - Given today=2024-02-28, dueDate=2024-02-28, isCompleted=false, return exactly "D-Day". `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_beforeLeapDay_returnsDDay`
  - Given today=2024-02-28, dueDate=2024-02-29, isCompleted=false, return exactly "D-1". `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_onLeapDay_returnsDMinus1`
  - Given today=2024-02-28, dueDate=2024-03-01, isCompleted=false, return exactly "D-2". `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_afterLeapDay_returnsDMinus2`
  - Given today=2024-03-01, dueDate=2024-02-28, isCompleted=false, return exactly "D+2". `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_pastLeapDay_returnsDPlus2`
  - Given today=2025-02-28, dueDate=2025-03-01, isCompleted=false, return exactly "D-1". `[Unit]`
    - Tested by `DueDateUtilsTest.dueDateLabel_nonLeapMarch_returnsDMinus1`

**Undefined behavior** (optional)
- None for typed inputs.
