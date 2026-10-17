# Spec: `TasksListBindings.kt`

> Post-conditions are the test oracle: write the expected behavior from intent, not from the code.
> Tag each post-condition [Unit] if its test runs on the JVM; otherwise tag it [Integration].
> End each valid case with `Tested by <test>` if an existing test checks it, or `Tip:` describing the test to write.

## `setDueDateLabel(textView: TextView, task: Task?): Unit`

**Intent**: Replace both text and visibility on every binding, including recycled views and task state changes. No label means text="" and visibility=GONE.

**Pre-conditions**
- Run on the Android main thread with a valid TextView. T is LocalDate.now() captured at fixture creation; dated calls run immediately on that thread. For example, T=2026-10-17 means T+1=2026-10-18 and T-1=2026-10-16.

**Post-conditions**
- Success cases
  - Given today=T and active dueDate=T+1 day, set text="D-1" and visibility=VISIBLE. `[Integration]`
    - Tested by `TasksListBindingsTest.setDueDateLabel_futureDate_showsDMinus1`
  - Given today=T and active dueDate=T, set text="D-Day" and visibility=VISIBLE. `[Integration]`
    - Tested by `TasksListBindingsTest.setDueDateLabel_today_showsDDay`
  - Given today=T and active dueDate=T-1 day, set text="D+1" and visibility=VISIBLE. `[Integration]`
    - Tested by `TasksListBindingsTest.setDueDateLabel_pastDate_showsDPlus1`
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
  - Reuse one TextView and task id="task-1" with dueDate=null. Active -> completed -> active must show "CLEAR"/VISIBLE at completion, then ""/GONE after activation. `[Integration]`
    - Tested by `TasksListBindingsTest.setDueDateLabel_completeThenActivateWithoutDate_clearsAndHides`
  - Reuse one TextView: active dueDate=T+1 day -> dueDate=null must change "D-1"/VISIBLE to ""/GONE. `[Integration]`
    - Tested by `TasksListBindingsTest.setDueDateLabel_removeDueDate_clearsAndHides`
  - Reuse one TextView: completed task -> null must change "CLEAR"/VISIBLE to ""/GONE. `[Integration]`
    - Tested by `TasksListBindingsTest.setDueDateLabel_clearTaskAfterCompleted_clearsAndHides`
  - Reuse a GONE TextView: null -> active dueDate=T+1 day must show "D-1"/VISIBLE. `[Integration]`
    - Tested by `TasksListBindingsTest.setDueDateLabel_reuseHiddenViewWithDate_showsLabel`
  - Reuse one TextView and task id="task-1", dueDate=T+1 day: active -> completed -> active must display "D-1" -> "CLEAR" -> "D-1", always VISIBLE. `[Integration]`
    - Tested by `TasksListBindingsTest.setDueDateLabel_completeThenActivateWithDate_restoresLabel`

**Undefined behavior** (optional)
- Concurrent system-date changes during one main-thread binding sequence are excluded because this API has no injected Clock. GONE plus empty text is an explicit acceptance decision.
