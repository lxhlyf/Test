package com.test.model.event;


import com.test.model.entity.DiaryBean;

/**
 * 打开「修改日记」的界面
 *
 * Created by developerHaoz on 2017/5/3.
 */

public class StartUpdateDiaryEvent {

    private DiaryBean mDiaryBean;

    public StartUpdateDiaryEvent(DiaryBean diaryBean) {
        mDiaryBean = diaryBean;
    }

    public DiaryBean getDiaryBean() {
        return mDiaryBean;
    }
}
