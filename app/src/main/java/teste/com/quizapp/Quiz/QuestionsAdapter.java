package teste.com.quizapp.Quiz;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import teste.com.quizapp.R;
import teste.com.quizapp.Util.Cache;


public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {

    private Context context;
    private ViewHolder viewHolder;

    public QuestionsAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clickable_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        viewHolder = holder;
        viewHolder.setIsRecyclable(false);
        viewHolder.questionNumber.setText(String.valueOf(position + 1));
        setBackgroundColor(position);
    }

    public void setBackgroundColor(int position) {
        if (position >= Cache.questions.size())
            viewHolder.questionNumber.setBackgroundColor(context
                    .getResources().getColor(android.R.color.darker_gray));
        else if (Cache.questions.get(position).getResult() == null)
            viewHolder.questionNumber.setBackgroundColor(context
                    .getResources().getColor(android.R.color.holo_blue_light));
        else if (Cache.questions.get(position).getResult())
            viewHolder.questionNumber.setBackgroundColor(context
                    .getResources().getColor(R.color.colorPrimaryLight));
        else
            viewHolder.questionNumber.setBackgroundColor(context
                    .getResources().getColor(android.R.color.holo_red_light));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView questionNumber;

        private ViewHolder(View itemView) {
            super(itemView);
            questionNumber = itemView.findViewById(R.id.question_number);
        }
    }
}
