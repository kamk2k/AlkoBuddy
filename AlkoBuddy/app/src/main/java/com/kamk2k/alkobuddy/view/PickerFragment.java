package com.kamk2k.alkobuddy.view;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.kamk2k.alkobuddy.R;
import com.kamk2k.alkobuddy.view.utils.DrinkClickListener;
import com.kamk2k.alkobuddy.view.utils.DrinksGridAdapter;
import com.kamk2k.alkobuddy.model.DrinkItem;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PickerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PickerFragment extends Fragment {

    private static final String TAG = PickerFragment.class.getSimpleName();

    @InjectView(R.id.drinks_list) GridView drinksGridView;

    private DrinksGridAdapter drinksGridAdapter;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PickerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PickerFragment newInstance(String param1, String param2) {
        PickerFragment fragment = new PickerFragment();
        return fragment;
    }

    public PickerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.picker_fragment, container, false);
        ButterKnife.inject(this, root);
        drinksGridAdapter = new DrinksGridAdapter(getActivity());
        for(int i = 0; i < 20; i++) {
            drinksGridAdapter.addItem(DrinkItem.generateMock());
        }
        drinksGridView.setAdapter(drinksGridAdapter);
        drinksGridView.setOnItemClickListener(new DrinkClickListener());
        return root;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
