package info.ribosoft.mywork.HttpConn;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

import info.ribosoft.mywork.R;

public class RecyclerListaOrariAdapter extends
        RecyclerView.Adapter<RecyclerListaOrariAdapter.RecyclerListaOrariHolder> {
    // creating a variable for our array list and context
    private final ArrayList<RecyclerOrari> courseDataArrayListOrari;
    private final Context context;
    private final String[] strGiornoNome =
        {"Lunedì ", "Martedì ", "Mercoledì ", "Giovedì ", "Venerdì ", "Sabato ", "Domenica "};

    // creating a constructor class
    public RecyclerListaOrariAdapter(ArrayList<RecyclerOrari> recyclerDataArrayListOrari,
        Context context) {
        this.courseDataArrayListOrari = recyclerDataArrayListOrari;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerListaOrariAdapter.RecyclerListaOrariHolder onCreateViewHolder(@NonNull ViewGroup
        parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orarisettimanali_card,
            parent, false);
        return new RecyclerListaOrariAdapter.RecyclerListaOrariHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerListaOrariAdapter.RecyclerListaOrariHolder holder,
        int position) {
        // Set the data to textview from our modal class
        RecyclerOrari modal = courseDataArrayListOrari.get(position);

        holder.courseSettGiorno.setText(modal.getData().substring(6,8));

        String strDepoData = modal.getData().substring(4,6) + "/" + modal.getData().substring(0,4);
        holder.courseSettData.setText(strDepoData);

        int depo=position/7; depo=position-depo*7;
        holder.courseSettNome.setText(strGiornoNome[depo]);

        if (modal.getDataSel().equals("1")) {
            holder.courseRigaDay.setBackgroundColor(ContextCompat.getColor(context,
                R.color.colorRigaCurr));
        }
        if (position == 5) {
            holder.courseSettGiorno.setTextColor(ContextCompat.getColor(context,
                R.color.colorPrefestivo));
            holder.courseSettData.setTextColor(ContextCompat.getColor(context,
                R.color.colorPrefestivo));
            holder.courseSettNome.setTextColor(ContextCompat.getColor(context,
                R.color.colorPrefestivo));
        }
        if (position == 6) {
            holder.courseSettGiorno.setTextColor(ContextCompat.getColor(context,
                R.color.colorFestivo));
            holder.courseSettData.setTextColor(ContextCompat.getColor(context,
                R.color.colorFestivo));
            holder.courseSettNome.setTextColor(ContextCompat.getColor(context,
                R.color.colorFestivo));
        }

        String strDepo = modal.getOrario();
        if (strDepo.length() > 1) {
            String strDepoIn = strDepo.substring(0,2) + ":" + strDepo.substring(2,4);
            holder.courseSettIngresso.setText(strDepoIn);
            String strDepoOut =strDepo.substring(0,2);
            int i = Integer.parseInt(strDepoOut) + Integer.parseInt(modal.getOre());
            strDepo = String.format(Locale.ITALIAN, "%02d:", i) + strDepo.substring(2,4);
            holder.courseSettUscita.setText(strDepo);
            holder.courseSettNote.setText("");
        } else {

            if (modal.getOrarioTipo().equals("R")) holder.courseSettNote.setText(R.string.riposo);
            if (modal.getOrarioTipo().equals("L")) holder.courseSettNote.setText(R.string.libero);
            if (modal.getOrarioTipo().equals("F")) holder.courseSettNote.setText(R.string.ferie);
            holder.courseSettIngresso.setText("");
            holder.courseSettUscita.setText("");
        }
    }

    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return courseDataArrayListOrari.size();
    }

    // View Holder Class to handle Recycler View
    public static class RecyclerListaOrariHolder extends RecyclerView.ViewHolder {
        // creating variables for our views
        private final LinearLayout courseRigaDay;
        private final TextView courseSettGiorno, courseSettData, courseSettNome,
            courseSettIngresso, courseSettUscita, courseSettNote;

        public RecyclerListaOrariHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our views with their ids
            courseRigaDay = itemView.findViewById(R.id.llyRigaDay);
            courseSettGiorno = itemView.findViewById(R.id.txtListaSettGiorno);
            courseSettData = itemView.findViewById(R.id.txtListaSettData);
            courseSettNome = itemView.findViewById(R.id.txtListaSettNome);
            courseSettIngresso = itemView.findViewById(R.id.txtListaSettIngresso);
            courseSettUscita = itemView.findViewById(R.id.txtListaSettUscita);
            courseSettNote = itemView.findViewById(R.id.txtListaSettNote);
        }
    }
}
