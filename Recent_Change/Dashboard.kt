package com.su.mynavigation

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Dashboard : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        // Inflate the layout for this fragment
        //val stepsTodayButton = view.findViewById<ImageButton>(R.id.stepsTodayButton)
        //TooltipCompat.setTooltipText(stepsTodayButton, "Your tooltip text here")
        val milestoneButton = view.findViewById<ImageButton>(R.id.milestoneGoalButton)
        val imageButton = view.findViewById<ImageButton>(R.id.stepsTodayButton3)
        imageButton.setOnClickListener {
            displayAlertDialog()
        }
        milestoneButton.setOnClickListener{
            changeMilestoneGoal()
        }


        return view
        // return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Dashboard.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Dashboard().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    /**
     * This function is used to change weekly/daily goal when user presses the setting looklike
     * button.
     * More functions can be added here !!!
     */
    private fun displayAlertDialog() {
        val rootView = requireView()
        val week_component = rootView.findViewById<View>(R.id.weeklyGoalText)
        val day_component = rootView.findViewById<View>(R.id.dailyGoalText)
        var componentId = rootView.findViewById<View>(R.id.weeklyGoalText).visibility
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("Setting")
        // if the appeared text is weekly goal
        if (componentId == View.VISIBLE) {

            alertDialogBuilder.setMessage("Do you want to change it to daily goal")
            alertDialogBuilder.setPositiveButton("OK") { dialog, which ->
                // actions when user pressing OK button
                week_component.visibility = View.INVISIBLE
                day_component.visibility = View.VISIBLE
                dialog.dismiss()
            }

            alertDialogBuilder.setNegativeButton("Cancel") { dialog, which ->
                // if user press cancel, nothing changes
                dialog.dismiss()
            }



        }
        // else the appeared text is daily goal, then the changing will be to weekly goal.
        else{
            alertDialogBuilder.setMessage("Do you want to change it to weekly goal")
            alertDialogBuilder.setPositiveButton("OK") { dialog, which ->
                day_component.visibility = View.INVISIBLE
                week_component.visibility = View.VISIBLE
                componentId = week_component.id
                dialog.dismiss()
            }
            alertDialogBuilder.setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }

        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }


    /**
     * This function is used to change the milestone goal.
     */
    private fun changeMilestoneGoal(){
        val rootView = requireView()
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Do you want to change Milestone Goal ?")

        val input = EditText(requireContext())
        input.inputType = InputType.TYPE_CLASS_NUMBER
        builder.setView(input)

        builder.setPositiveButton("OK") { dialog, which ->
            // get user input
            val newGoal = input.text.toString().toIntOrNull()
            if (newGoal != null) {
                updateMilestoneGoal(newGoal)
            } else {
                dialog.cancel()
            }
        }

        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.cancel()
        }
        builder.show()

    }

    /**
     * update the changed milestone steps from user, change the number in dashboard.xml
     */
    private fun updateMilestoneGoal(newGoal: Int){
        val rootView = requireView()
        val milestoneGoal = rootView.findViewById<TextView>(R.id.milestoneNumberText)
        val newGoalString = newGoal.toString()

        milestoneGoal.text = newGoalString
    }

}