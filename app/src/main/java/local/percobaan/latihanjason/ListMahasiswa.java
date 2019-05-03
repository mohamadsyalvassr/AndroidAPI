package local.percobaan.latihanjason;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.HashMap;

public class ListMahasiswa extends RecyclerView.Adapter<ListMahasiswa.ViewHolder> {
    Context context;
    ArrayList<HashMap<String, String>> list_data;

    public ListMahasiswa(MainActivity mainActivity, ArrayList<HashMap<String, String>> list_data){
        this.context = mainActivity;
        this.list_data = list_data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listma, null);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context)
                .load("http://192.168.100.2/sms6_laravel/public/storage/images/300/"+list_data.get(position).get("nama_foto"))
                .crossFade()
                .placeholder(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imghape);
        holder.txtnama.setText(list_data.get(position).get("namadosen"));
        holder.txtalamat.setText("NIDN "+list_data.get(position).get("nidn"));

//        holder.txtnim.setText("NIM "+list_data.get(position).get("NIM"));
//        holder.txtnama.setText("NAMA "+list_data.get(position).get("NAMA"));
//        holder.txtalamat.setText(list_data.get(position).get("JURUSAN"));
    }
    @Override
    public int getItemCount(){
        return list_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtnama, txtnim, txtalamat;
        ImageView imghape;

        public ViewHolder(View itemView){
            super(itemView);
            txtnama = (TextView) itemView.findViewById(R.id.txtnama);
            txtalamat = (TextView) itemView.findViewById(R.id.txtalamat);
            imghape = (ImageView) itemView.findViewById(R.id.imghp);
        }
    }
}