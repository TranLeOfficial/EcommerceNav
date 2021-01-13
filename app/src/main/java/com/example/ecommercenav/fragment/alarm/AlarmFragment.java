package com.example.ecommercenav.fragment.alarm;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommercenav.Cart.ShowCart;
import com.example.ecommercenav.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AlarmFragment extends Fragment {

    private AlarmViewModel alarmViewModel;
    private RecyclerView list_Alarm, list_AlarmShip;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference alarmRef;
    private DatabaseReference alarmRefShip;


    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        alarmViewModel =
                ViewModelProviders.of(this).get(AlarmViewModel.class);
        View root = inflater.inflate(R.layout.fragment_alarm, container, false);
        // final TextView textView = root.findViewById(R.id.text_slideshow);
//        alarmViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        list_Alarm = root.findViewById(R.id.list_Alarm);
        layoutManager = new LinearLayoutManager(getContext());
        list_Alarm.setHasFixedSize(true);
        list_Alarm.setLayoutManager(layoutManager);


        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

//        alarmRefShip = FirebaseDatabase.getInstance().getReference("AlarmShip");
//        FirebaseRecyclerOptions<AlarmModel> optionsA = new FirebaseRecyclerOptions.Builder<AlarmModel>()
//                .setQuery(alarmRefShip.child(firebaseUser.getUid()), AlarmModel.class).build();
//        FirebaseRecyclerAdapter<AlarmModel, AlarmAdapterViewHolder> adapterA = new FirebaseRecyclerAdapter<AlarmModel, AlarmAdapterViewHolder>(optionsA) {
//            @Override
//            protected void onBindViewHolder(@NonNull AlarmAdapterViewHolder holder, int position, @NonNull final AlarmModel alarmModel) {
//                holder.alarm_date_time.setText("Thời gian xóa: "+alarmModel.getTime() + "   " + alarmModel.getDated());
//                holder.alarm_phone.setText("SĐT " + alarmModel.getPhone());
//                holder.alarm_mess.setText("Lý do xóa: " + alarmModel.getMess());
//                holder.alarm_time_oder.setText("Đơn hàng đã đặt lúc: "+alarmModel.getTime_oder());
//                holder.alarm_price.setText("Trị giá: " + alarmModel.getPrice());
//
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        CharSequence options[] = new CharSequence[]
//                                {
//                                        "Xóa"
//                                };
//
//
//                        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                        builder.setTitle("Xóa thông báo");
//                        builder.setItems(options, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(final DialogInterface dialog, int which) {
//                                if (which == 0) {
//                                    alarmRef.child(firebaseUser.getUid())
//                                            .child(alarmModel.getPhone())
//                                            .removeValue()
//                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<Void> task) {
//                                                    if (task.isSuccessful()) {
//                                                        Toast.makeText(getContext(), "Xóa Thành Công!", Toast.LENGTH_SHORT).show();
//                                                        dialog.dismiss();
//                                                    }
//                                                }
//                                            });
//                                }
//                            }
//                        });
//                        builder.show();
//                    }
//                });
//            }
//
//            @NonNull
//            @Override
//            public AlarmAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_alarm, parent, false);
//                return new AlarmAdapterViewHolder(view);
//            }
//        };


        alarmRef = FirebaseDatabase.getInstance().getReference("Alarm");
        FirebaseRecyclerOptions<AlarmModel> options = new FirebaseRecyclerOptions.Builder<AlarmModel>()
                .setQuery(alarmRef.child(firebaseUser.getUid()), AlarmModel.class).build();
        FirebaseRecyclerAdapter<AlarmModel, AlarmAdapterViewHolder> adapter = new FirebaseRecyclerAdapter<AlarmModel, AlarmAdapterViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AlarmAdapterViewHolder holder, int position, @NonNull final AlarmModel alarmModel) {
                holder.alarm_date_time.setText("Thời gian xóa: "+alarmModel.getTime() + "   " + alarmModel.getDated());
                holder.alarm_phone.setText("SĐT " + alarmModel.getPhone());
                holder.alarm_mess.setText("Lý do xóa: " + alarmModel.getMess());
                holder.alarm_time_oder.setText("Đơn hàng đã đặt lúc: "+alarmModel.getTime_oder());
                holder.alarm_price.setText("Trị giá: " + alarmModel.getPrice());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence options[] = new CharSequence[]
                                {
                                        "Xóa"
                                };

                        
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Xóa thông báo");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {
                                if (which == 0) {
                                    alarmRef.child(firebaseUser.getUid())
                                            .child(alarmModel.getPhone())
                                            .removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(getContext(), "Xóa Thành Công!", Toast.LENGTH_SHORT).show();
                                                        dialog.dismiss();
                                                    }
                                                }
                                            });
                                }
                            }
                        });
                        builder.show();
                    }
                });
            }

            @NonNull
            @Override
            public AlarmAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_alarm, parent, false);
                return new AlarmAdapterViewHolder(view);
            }
        };
        list_Alarm.setAdapter(adapter);
        adapter.startListening();
    }

    public static class AlarmAdapterViewHolder extends RecyclerView.ViewHolder {
        public TextView alarm_date_time, alarm_phone, alarm_mess, alarm_time_oder, alarm_price;

        public AlarmAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            alarm_date_time = itemView.findViewById(R.id.alarm_date_time);
            alarm_phone = itemView.findViewById(R.id.alarm_phone);
            alarm_mess = itemView.findViewById(R.id.alarm_mess);
            alarm_time_oder = itemView.findViewById(R.id.alarm_time_oder);
            alarm_price = itemView.findViewById(R.id.alarm_price);

        }
    }

}