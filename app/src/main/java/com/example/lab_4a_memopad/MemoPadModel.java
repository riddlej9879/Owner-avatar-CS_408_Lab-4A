package com.example.lab_4a_memopad;

public class MemoPadModel extends AbstractModel {
    private final DatabaseHandler db;

    public MemoPadModel (MainActivity mainActivity) {
        db = new DatabaseHandler(mainActivity, null, null, 1);
    }

    public void addMemo(String newMemo) {
        Memo memo = new Memo(newMemo);

        db.addMemo(memo);
        firePropertyChange(MemoPadController.CONTROLLER_PROPERTY, null, db.getAllMemosAsList());
    }
    public void delMemo(int id) {
        db.deleteMemo(id);
        firePropertyChange(MemoPadController.CONTROLLER_PROPERTY, null, db.getAllMemosAsList());
    }
    public void getAllMemos() {
        firePropertyChange(MemoPadController.CONTROLLER_PROPERTY, null, db.getAllMemosAsList());
    }
}