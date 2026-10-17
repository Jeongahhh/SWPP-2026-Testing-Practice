# Spec: `TasksListBindings.kt`

> Post-conditions are the test oracle: write the expected behavior from intent, not from the code.
> Tag each post-condition [Unit] if its test runs on the JVM; otherwise tag it [Integration].
> End each valid case with `Tested by <test>` if an existing test checks it, or `Tip:` describing the test to write.

## `setDueDateLabel(textView: TextView, task: Task?): Unit`

**Intent**: Render the task D-day label, or clear and hide an absent label.

**Pre-conditions**
- Run on the Android main thread with a valid TextView. T is LocalDate.now() captured at fixture creation; dated calls run immediately on that thread.

**Post-conditions**
- Success cases
  - Given completed=true and dueDate=null, set text="CLEAR" and visibility=VISIBLE. `[Integration]`
    - Tested by `TasksListBindingsTest.setDueDateLabel_completedWithoutDate_showsClear`
  - Given completed=true and dueDate=T+1 day, set text="CLEAR" and visibility=VISIBLE. `[Integration]`
    - Tested by `TasksListBindingsTest.setDueDateLabel_completedWithDate_showsClear`
  - Given an active task with dueDate=null and old text="stale label", clear text to "" and set visibility=GONE. `[Integration]`
    - Tested by `TasksListBindingsTest.setDueDateLabel_activeWithoutDate_clearsAndHides`
  - Given task=null and old text="stale label", clear text to "" and set visibility=GONE. `[Integration]`
    - Tested by `TasksListBindingsTest.setDueDateLabel_nullTask_clearsAndHides`
- Error cases
  - None. Null is a valid absence value, not an error. `[Integration]`
    - Tested by `TasksListBindingsTest.setDueDateLabel_nullTask_clearsAndHides`
- Edge cases
  - Given today=T and active dueDate=T+1 day, set text="D-1" and visibility=VISIBLE. `[Integration]`
    - Tested by `TasksListBindingsTest.setDueDateLabel_futureDate_showsDMinus1`
  - Given today=T and active dueDate=T, set text="D-Day" and visibility=VISIBLE. `[Integration]`
    - Tested by `TasksListBindingsTest.setDueDateLabel_today_showsDDay`
  - Given today=T and active dueDate=T-1 day, set text="D+1" and visibility=VISIBLE. `[Integration]`
    - Tested by `TasksListBindingsTest.setDueDateLabel_pastDate_showsDPlus1`

**Undefined behavior** (optional)
- A clock change during one binding sequence is outside this draft. GONE plus empty text is the explicit interpretation of no label.
