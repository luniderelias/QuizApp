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

import static teste.com.quizapp.Model.Question.Question.NOT_ANSWERED_CODE;
import static teste.com.quizapp.Model.Question.Question.RIGHT_ANSWER_CODE;
import static teste.com.quizapp.Model.Question.Question.WRONG_ANSWER_CODE;

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
        return Cache.questions.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        viewHolder = holder;
        viewHolder.setIsRecyclable(false);
        viewHolder.questionNumber.setText(String.valueOf(position+1));
        setBackgroundColor(position);
    }

    public void setBackgroundColor(int position){
        switch (Cache.questions.get(position).getResult()){
            case NOT_ANSWERED_CODE:
                viewHolder.questionNumber.setBackgroundColor(context
                        .getResources().getColor(android.R.color.holo_blue_light));
                break;
            case RIGHT_ANSWER_CODE:
                viewHolder.questionNumber.setBackgroundColor(context
                        .getResources().getColor(R.color.colorPrimaryLight));       break;
            case WRONG_ANSWER_CODE:
                viewHolder.questionNumber.setBackgroundColor(context
                        .getResources().getColor(android.R.color.holo_red_light));
                break;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView questionNumber;

        private ViewHolder(View itemView) {
            super(itemView);
            questionNumber = itemView.findViewById(R.id.question_number);
        }
    }
}
