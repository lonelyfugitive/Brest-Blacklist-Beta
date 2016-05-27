package bignerdrunch.brestblacklistgen.new_crime_dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bignerdrunch.brestblacklistgen.R;
import bignerdrunch.brestblacklistgen.Utils;
import bignerdrunch.brestblacklistgen.model.ModelCard;

public class AddingCrimeDialogFragment extends DialogFragment {

    private AddingCrimeListener addingCrimeListener;

    public interface AddingCrimeListener {
        void onCrimeAdded(ModelCard newCrime);

        void onCrimeAddingCanceled();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            addingCrimeListener = (AddingCrimeListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement AddingCrimeListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.new_crime_dialog_label);

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View container = inflater.inflate(R.layout.dialog_crime, null);


        final TextInputLayout tilTitle = (TextInputLayout) container.findViewById(R.id.tilDialogCrimeTitle);
        final EditText etTitle = tilTitle.getEditText();

        TextInputLayout tilDate = (TextInputLayout) container.findViewById(R.id.tilDialogCrimeDate);
        final EditText etDate = tilDate.getEditText();

        TextInputLayout tilTime = (TextInputLayout) container.findViewById(R.id.tilDialogCrimeTime);
        final EditText etTime = tilTime.getEditText();

        final ModelCard crime = new ModelCard();

        final TextView spinnerHint = (TextView) container.findViewById(R.id.hashtag_spinner_hint);
        final FrameLayout spinnerFrame = (FrameLayout) container.findViewById(R.id.spinner_frame);

        String hashTagBeauty = getResources().getString(R.string.hashtag_beauty);
        String hashTagBuy = getResources().getString(R.string.hashtag_buy);
        String hashTagFun = getResources().getString(R.string.hashtag_fun);
        String hashTagPub = getResources().getString(R.string.hashtag_pub);
        String hashTagTransport = getResources().getString(R.string.hashtag_transport);

        final Spinner spHashtag = (Spinner) container.findViewById(R.id.spDialogCrime);
        List<String> hashtagAssignment = new ArrayList<String>();
        hashtagAssignment.add(hashTagBeauty);
        hashtagAssignment.add(hashTagBuy);
        hashtagAssignment.add(hashTagFun);
        hashtagAssignment.add(hashTagPub);
        hashtagAssignment.add(hashTagTransport);

        ArrayAdapter<String> hashtagAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, hashtagAssignment);

        spHashtag.setAdapter(hashtagAdapter);
        spHashtag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                crime.setHashtag((String) adapterView.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                crime.setHashtag((String) adapterView.getItemAtPosition(1));
            }
        });

        tilTitle.setHint(getResources().getString(R.string.new_title_hint));
        tilDate.setHint(getResources().getString(R.string.new_date_hint));
        tilTime.setHint(getResources().getString(R.string.new_time_hint));

        builder.setView(container);

        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 1);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etDate.length() == 0) {
                    etDate.setText(" ");
                }

                DialogFragment datePickerFragment = new DatePickerFragment() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        etDate.setText(Utils.getDate(calendar.getTimeInMillis()));
                    }

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        etDate.setText(null);
                    }
                };
                datePickerFragment.show(getFragmentManager(), "DatePickerFragment");
            }
        });

        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etTime.length() == 0) {
                    etTime.setText(" ");
                }

                DialogFragment timePickerFragment = new TimePickerFragment() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hour);
                        calendar.set(Calendar.MINUTE, minute);
                        calendar.set(Calendar.SECOND, 0);

                        etTime.setText(Utils.getTime(calendar.getTimeInMillis()));
                    }

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        etTime.setText(null);
                    }
                };
                timePickerFragment.show(getFragmentManager(), "TimePickerFragment");
            }
        });

        builder.setPositiveButton(R.string.dialog_OK_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                crime.setTitle(etTitle.getText().toString());

                if (etDate.length() != 0 || etTime.length() != 0) {
                    crime.setDate(calendar.getTimeInMillis());
                }
                addingCrimeListener.onCrimeAdded(crime);

                dialogInterface.dismiss();
            }
        });

        builder.setNegativeButton(R.string.dialog_cancel_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addingCrimeListener.onCrimeAddingCanceled();
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                final Button positiveButton = ((AlertDialog) dialogInterface).getButton(DialogInterface.BUTTON_POSITIVE);
                if (etTitle.length() == 0) {
                    positiveButton.setEnabled(false);
                    tilTitle.setError(getResources().getString(R.string.error_empty_title));
                    spinnerHint.setVisibility(View.INVISIBLE);
                    spHashtag.setEnabled(false);
                }

                etTitle.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (charSequence.length() == 0) {
                            positiveButton.setEnabled(false);
                            tilTitle.setError(getResources().getString(R.string.error_empty_title));
                            spHashtag.setEnabled(false);
                            spinnerHint.setVisibility(View.INVISIBLE);
                            spinnerFrame.setBackgroundColor(getResources().getColor(R.color.gray_200));
                        } else {
                            positiveButton.setEnabled(true);
                            tilTitle.setErrorEnabled(false);
                            spHashtag.setEnabled(true);
                            spinnerHint.setVisibility(View.VISIBLE);
                            spinnerFrame.setBackgroundColor(getResources().getColor(R.color.colorAccent));

                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
            }
        });

        return alertDialog;
    }
}
