package lxy.com.todonote.note;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import lxy.com.todonote.R;
import lxy.com.todonote.addnote.AddTodoNoteFragment;
import lxy.com.todonote.base.BaseActivity;
import lxy.com.todonote.base.BasePageModel;
import lxy.com.todonote.baseadapter.BaseAdapter;
import lxy.com.todonote.databinding.FragmentNoteBinding;
import lxy.com.todonote.net.Resource;
import lxy.com.todonote.utils.ToastUtils;

/**
 * @author lxy
 */
public class NoteFragment extends Fragment {

    private FragmentNoteBinding binding;
    private NoteViewModel viewModel;
    private NoteAdapter adapter;
    private int page = 0;
    public List<NoteModel> models;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_note, container, false);
        viewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initListener();
        observe();
    }

    private void initView() {
        models = new ArrayList<>();
        adapter = new NoteAdapter(models, R.layout.item_note);
        binding.noteRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.noteRv.setAdapter(adapter);
        binding.noteRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
    }

    private void initListener() {
        binding.noteRefresh.setOnRefreshListener(() -> {
            viewModel.refresh().observe(this, noteModels -> {
                models.clear();
                models.addAll(noteModels.data.getDatas());
                adapter.notifyDataSetChanged();
                binding.noteRefresh.setRefreshing(false);
            });
        });
        adapter.addItemChildClickListener((adapter, view, position) -> {
            NoteModel model = (NoteModel) adapter.getItemByPosition(position);
            switch (view.getId()) {
                case R.id.note_delete:
                    viewModel.deleteNote(model.getId()).observe(NoteFragment.this, stringResource -> stringResource.handler(new BaseActivity.OnCallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            ToastUtils.show("删除成功");
                            models.remove(position);
                            adapter.notifyItemRemoved(position);
                        }
                    }));
                    break;
                case R.id.cb_note_finish:
                    int status = model.getStatus() == 0 ? 1 : 0;
                    viewModel.updateNoteStatus(model.getId(), status).observe(NoteFragment.this, noteModelResource -> {
                        noteModelResource.handler(new BaseActivity.OnCallback<NoteModel>() {
                            @Override
                            public void onSuccess(NoteModel data) {
                                model.setStatus(data.getStatus());
                                adapter.notifyItemChanged(position);
                            }
                        });
                    });
                    break;
                default:
                    break;
            }
        });
        adapter.setOnItemListener((v,position) -> {
            NoteModel model = models.get(position);
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            AddTodoNoteFragment fragment = AddTodoNoteFragment.newInstance(model.getType(),new Gson().toJson(model));
            fragment.setTargetFragment(NoteFragment.this, 8250);
            transaction.add(R.id.container, fragment);
            transaction.addToBackStack("");
            transaction.commit();
        });
        binding.noteAdd.setOnClickListener(v -> {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            AddTodoNoteFragment fragment = AddTodoNoteFragment.newInstance(0,"");
            fragment.setTargetFragment(NoteFragment.this, 8250);
            transaction.add(R.id.container, fragment);
            transaction.addToBackStack("");
            transaction.commit();
        });
    }

    private void observe() {
        viewModel.getUndoList(page).observe(this, listResource -> listResource.handler(new BaseActivity.OnCallback<BasePageModel<NoteModel>>() {
            @Override
            public void onSuccess(BasePageModel<NoteModel> data) {
                if (page == 0) {
                    models.clear();
                }
                models.addAll(data.getDatas());
                page++;
                adapter.notifyDataSetChanged();
            }
        }));
    }
}
