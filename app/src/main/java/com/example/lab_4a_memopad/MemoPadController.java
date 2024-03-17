package com.example.lab_4a_memopad;

public class MemoPadController extends AbstractController {
    public static final String CONTROLLER_PROPERTY = "MemoList";
    private final MemoPadModel model;

    public MemoPadController(MemoPadModel model) {
        super();
        this.model = model;
        addModel(model);
    }

    public void addMemo(String newMemo) {
        model.addMemo(newMemo);
    }
    public void delMemo(int id) {
        model.delMemo(id);
    }
    public void getAllMemos() {
        model.getAllMemos();
    }
}