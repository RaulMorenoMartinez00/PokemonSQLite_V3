package es.android.pokemon.servicio.interfaz;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import es.android.pokemon.R;

import es.android.pokemon.databinding.FragmentItemListBinding;
import es.android.pokemon.databinding.ItemListContentBinding;
import es.android.pokemon.entidad.Pokemon;
import es.android.pokemon.repositorio.implementacion.RepositorioSQLiteImpl;

import java.util.List;

/**
 * A fragment representing a list of Items. This fragment
 * has different presentations for handset and larger screen devices. On
 * handsets, the fragment presents a list of items, which when touched,
 * lead to a {@link ItemDetailFragment} representing
 * item details. On larger screens, the Navigation controller presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListFragment extends Fragment {

    RepositorioSQLiteImpl pokemondb;
    private FragmentItemListBinding binding;
    public static RecyclerView recyclerView= null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentItemListBinding.inflate(inflater, container, false);

        return binding.getRoot();


    }
    
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ItemListFragment.this)
                        .navigate(R.id.item_list_to_form_fragment); // accion de navegacion
            }
        });

        View itemDetailFragmentContainer = view.findViewById(R.id.item_detail_fragment);

        pokemondb= new RepositorioSQLiteImpl(getContext());

        List<Pokemon> pokemonList = pokemondb.getAll();
        recyclerView = getActivity().findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView, pokemonList) ;
        }

    private void setupRecyclerView(RecyclerView recyclerView, List<Pokemon> items) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(items));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Pokemon> mValues;

        SimpleItemRecyclerViewAdapter(List<Pokemon> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            ItemListContentBinding binding = ItemListContentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolder(binding, this);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(Integer.toString(position));
            holder.mContentView.setText(mValues.get(position).getName());
            holder.itemView.setTag(position);
            holder.itemView.setOnClickListener(mOnClickListener);
        }
        private final View.OnClickListener mOnClickListener = new
                View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int index = (int) view.getTag();
                        Pokemon item = mValues.get(index);
                        Bundle arguments = new Bundle();
                        arguments.putInt(String.valueOf(ItemDetailFragment.ARG_ITEM_ID), index + 1);
                        arguments.putString(ItemDetailFragment.ARG_ITEM_NAME, item.getName());
                        arguments.putString(ItemDetailFragment.ARG_DESCRIPTION,item.getDescripcion());
                        Navigation.findNavController(view).navigate(R.id.show_item_detail, arguments);
                    }
                };

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;
            final Button mEliminarButton;

            ViewHolder(ItemListContentBinding binding, SimpleItemRecyclerViewAdapter adapter) {

                super(binding.getRoot());
                mIdView = binding.idText;
                mContentView = binding.content;
                mEliminarButton =binding.Eliminar;
                RepositorioSQLiteImpl db = new RepositorioSQLiteImpl(mContentView.getContext());

                mEliminarButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int id= Integer.parseInt(mIdView.getText().toString());
                       Pokemon pokemon=mValues.remove(id);

                        if(pokemon !=null){
                            db.delete(pokemon);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        }
    }
}