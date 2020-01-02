package lxy.com.todonote.addnote;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;

import com.google.gson.Gson;

import lxy.com.todonote.R;
import lxy.com.todonote.base.BaseActivity;
import lxy.com.todonote.base.BaseFragment;
import lxy.com.todonote.databinding.AddTodoNoteFragmentBinding;
import lxy.com.todonote.net.Resource;
import lxy.com.todonote.note.NoteFragment;
import lxy.com.todonote.note.NoteModel;
import lxy.com.todonote.utils.DateUtils;
import lxy.com.todonote.utils.ToastUtils;

/**
 * @author lxy
 */
public class AddTodoNoteFragment extends BaseFragment {

    private static final String MODEL_ADD = "add";
    private static final String MODEL_UPDATE = "update";
    private static final String NOTE_TYPE = "note_type";
    private static final String NOTE = "note";
    private AddTodoNoteViewModel mViewModel;
    private AddTodoNoteFragmentBinding binding;
    private String add_model = MODEL_ADD;
    private int type;
    private NoteModel noteModel;

    public static AddTodoNoteFragment newInstance(int type, String note) {
        AddTodoNoteFragment fragment = new AddTodoNoteFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(NOTE_TYPE, type);
        bundle.putString(NOTE, note);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_todo_note_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AddTodoNoteViewModel.class);
        binding.setAddmodel(mViewModel);
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt(NOTE_TYPE);
            String string = bundle.getString(NOTE);
            if (!TextUtils.isEmpty(string)) {
                noteModel = new Gson().fromJson(string, NoteModel.class);
                binding.addNoteTime.setText(noteModel.getDateStr());
                binding.addNoteTitle.setText(noteModel.getTitle());
                add_model = MODEL_UPDATE;
            }
        }
        binding.addNoteTitle.addTextChangedListener(titleWatcher);
        Drawable drawable = getResources().getDrawable(R.drawable.add_note_ic_clock);
        drawable.setBounds(0, 0, 40, 40);
        binding.addNoteTime.setCompoundDrawables(drawable, null, null, null);
        initListener();
        openInput();
    }

    private void openInput() {
        binding.addNoteTitle.post(new Runnable() {
            @Override
            public void run() {
                binding.addNoteTitle.setFocusable(true);
                binding.addNoteTitle.setFocusableInTouchMode(true);
                binding.addNoteTitle.requestFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null){
                    imm.showSoftInput(binding.addNoteTitle,InputMethodManager.SHOW_IMPLICIT);
                }
            }
        });
    }

    private void hideInput() {
        InputMethodManager inputmanger = (InputMethodManager) getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputmanger.hideSoftInputFromWindow(binding.addNoteTitle.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    private void initListener() {
        binding.addNoteComplete.setOnClickListener(v -> {
            String title = binding.addNoteTitle.getText().toString();
            String dateStr = binding.addNoteTime.getText().toString();
            if (MODEL_ADD.equals(add_model)) {
                addNote(title, dateStr);
            } else {
                updateNote(noteModel.getId(), title, dateStr);
            }
        });
        binding.addNoteTime.setOnClickListener(v -> {
            selectTime();
        });
    }

    private void updateNote(int id, String title, String dateStr) {
        mViewModel.updateNote(id, title, "", dateStr, type).observe(AddTodoNoteFragment.this, new Observer<Resource<NoteModel>>() {
            @Override
            public void onChanged(Resource<NoteModel> resource) {
                resource.handler(new BaseFragment.OnCallback<NoteModel>() {
                    @Override
                    public void onSuccess(NoteModel data) {
                        ToastUtils.show("修改成功");
                        Fragment fragment = getTargetFragment();
                        if (fragment != null) {
                            NoteFragment frag = (NoteFragment) fragment;
                            frag.refresh();
                        }
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                });
            }
        });
    }

    private void addNote(String title, String dateStr) {
        if (getString(R.string.add_note_set_time).equals(dateStr)) {
            dateStr = DateUtils.getDateString(System.currentTimeMillis());
        }
        mViewModel.addNote(title, "", dateStr, type, 1).observe(AddTodoNoteFragment.this, new Observer<Resource<NoteModel>>() {
            @Override
            public void onChanged(Resource<NoteModel> resource) {
                resource.handler(new BaseFragment.OnCallback<NoteModel>() {
                    @Override
                    public void onSuccess(NoteModel data) {
                        ToastUtils.show("创建成功");
//                        mViewModel.addAlarmToCalendar(data,getContext());
                        Fragment fragment = getTargetFragment();
                        if (fragment != null) {
                            NoteFragment frag = (NoteFragment) fragment;
                            frag.refresh();
                        }
                        hideInput();
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                });
            }
        });
    }

    private void selectTime() {
        new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String dateStr = year + "-" + (month + 1) + "-" + dayOfMonth;
                binding.addNoteTime.setText(dateStr);
            }
        }, 2019, 12, 12).show();
    }

    private TextWatcher titleWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (TextUtils.isEmpty(s)) {
                binding.addNoteComplete.setClickable(false);
                binding.addNoteComplete.setTextColor(getResources().getColor(R.color.textGray));
            } else {
                binding.addNoteComplete.setTextColor(getResources().getColor(R.color.colorAccent));
                binding.addNoteComplete.setClickable(true);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onDetach() {
        super.onDetach();
        hideInput();
    }
}
