package com.example.lab_4a_memopad;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.beans.PropertyChangeEvent;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.lab_4a_memopad.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements AbstractView {
    private final HandleButtonClick click_btn = new HandleButtonClick();
    private final HandleItemClick clickedItem = new HandleItemClick();

    private ActivityMainBinding binding;
    private MemoPadController controller;

    private int memoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.addMemoBtn.setOnClickListener(click_btn);
        binding.delMemoBtn.setOnClickListener(click_btn);

        controller = new MemoPadController(new MemoPadModel(this));
        controller.addView(this);
        controller.getAllMemos();
    }

    public HandleItemClick getClickedItem() {
        return clickedItem;
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent event) {
        String propertyName = event.getPropertyName();

        if ( propertyName.equals(MemoPadController.CONTROLLER_PROPERTY) ) {
            List<Memo> data = (List)event.getNewValue();

            RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, data);
            binding.output.setHasFixedSize(true);
            binding.output.setLayoutManager(new LinearLayoutManager(this));
            binding.output.setAdapter(adapter);
        }
    }

    private class HandleButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String btn_tag = view.getTag().toString();

            switch (btn_tag) {
                case "add":
                    String newMemo = binding.textInput.getText().toString();
                    controller.addMemo(newMemo);
                    break;
                case "delete":
                    controller.delMemo(memoId);
                    break;
            }
        }
    }

    private class HandleItemClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int itemPosition = binding.output.getChildLayoutPosition(view);
            RecyclerViewAdapter adapter = (RecyclerViewAdapter)binding.output.getAdapter();

            if (adapter != null) {
                Memo memo = adapter.getItemPosition(itemPosition);
                memoId = memo.getId();
                Toast.makeText(view.getContext(), String.valueOf(memoId), Toast.LENGTH_SHORT).show();
            }
        }
    }
}