package eizougraphic.sintret.hpstore;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.ContentHandler;
import java.util.List;

/**
 * Created by andi on 1/6/2016.
 */
public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {
    private SessionManager session;
    Context ctx;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView description,ago;
        ImageView imageView;

        // each data item is just a string in this case
        public TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            cv = (CardView) view.findViewById(R.id.cv);
            description = (TextView) view.findViewById(R.id.description);
            ago = (TextView) view.findViewById(R.id.ago);
            imageView = (ImageView) view.findViewById(R.id.imageView);
        }
    }

    List<JSONObject> messages;

    public MessagesAdapter(List<JSONObject> messages) {
        this.messages = messages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_report, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        JSONObject jsonObject = messages.get(position);
        String mAgo = "";
        Long mTime;
        String mDescription = "";
        int category_id =1;

        try {
            mAgo = jsonObject.getString("ago");
            mTime = jsonObject.getLong("updated_at");
            mDescription = jsonObject.getString("title");
            category_id = jsonObject.getInt("type");
            String dateAgo = DateUtil.getTimeAgo(mTime,ctx);
            holder.ago.setText(dateAgo);

            //Toast.makeText(," "+mDescription, Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        holder.description.setText(mDescription + "test");
        if(category_id==1){
            holder.imageView.setImageResource(R.mipmap.ic_beenhere_black_36dp);
        } else if(category_id==2){
            holder.imageView.setImageResource(R.mipmap.ic_event_note_black_36dp);
        } else if(category_id==3){
            holder.imageView.setImageResource(R.mipmap.ic_forward_black_36dp);
        } else if(category_id==4){
            holder.imageView.setImageResource(R.mipmap.ic_add_black_36dp);
        } else if(category_id==5){
            holder.imageView.setImageResource(R.mipmap.ic_local_post_office_black_36dp);
        } else {
            holder.imageView.setImageResource(R.mipmap.ic_launcher);
        }

    }

   /* @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }*/

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return messages.size();
    }
}

