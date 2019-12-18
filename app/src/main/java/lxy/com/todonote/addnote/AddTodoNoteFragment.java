package lxy.com.todonote.addnote;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lxy.com.todonote.R;
import lxy.com.todonote.databinding.AddTodoNoteFragmentBinding;

public class AddTodoNoteFragment extends Fragment {

    private AddTodoNoteViewModel mViewModel;
    private AddTodoNoteFragmentBinding binding;

    public static AddTodoNoteFragment newInstance() {
        return new AddTodoNoteFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.add_todo_note_fragment,container,false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AddTodoNoteViewModel.class);
        binding.setAddmodel(mViewModel);
    }

}
