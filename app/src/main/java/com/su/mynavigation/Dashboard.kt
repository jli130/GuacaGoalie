package com.su.mynavigation

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private var stepsTodayText: TextView? = null
/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Dashboard : Fragment(){
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    private var currentStepCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stepsTodayText = view.findViewById(R.id.stepsTodayText)
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
        imageButton?.setOnClickListener {
            // displayAlertDialog()
        }
        milestoneButton?.setOnClickListener {
            //changeMilestoneGoal()
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
    private fun displayAlertDialog(rootView: View, mainIns: MainActivity, db: DatabaseHelper) {
        // val rootView = requireView()
        val week_component = rootView.findViewById<View>(R.id.weeklyGoalText)
        val day_component = rootView.findViewById<View>(R.id.dailyGoalText)
        val switch = rootView.findViewById<TextView>(R.id.weeklyGoalNumberText)
        val alertDialogBuilder = AlertDialog.Builder(mainIns)
        alertDialogBuilder.setTitle("Setting")

        val isWeeklyGoalVisible = week_component.visibility == View.VISIBLE
        if (isWeeklyGoalVisible) {
            alertDialogBuilder.setMessage("Do you want to change it to daily goal")
            alertDialogBuilder.setPositiveButton("OK") { dialog, which ->
                // actions when user presses OK button
                week_component.visibility = View.INVISIBLE
                day_component.visibility = View.VISIBLE
                switch.text = (db.getAllInfo()[0].goal/7).toString()
                dialog.dismiss()
            }
            alertDialogBuilder.setNegativeButton("Cancel") { dialog, which ->
                // if user presses cancel, nothing changes
                dialog.dismiss()
            }
        } else {
            alertDialogBuilder.setMessage("Do you want to change it to weekly goal")
            alertDialogBuilder.setPositiveButton("OK") { dialog, which ->
                day_component.visibility = View.INVISIBLE
                week_component.visibility = View.VISIBLE
                switch.text = db.getAllInfo()[0].goal.toString()
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
    private fun changeMilestoneGoal(mainIns: MainActivity, db: DatabaseHelper) {

        val builder = AlertDialog.Builder(mainIns)
        builder.setTitle("Do you want to change Milestone Goal ?")

        val input = EditText(mainIns)
        input.inputType = InputType.TYPE_CLASS_NUMBER
        builder.setView(input)

        builder.setPositiveButton("OK") { dialog, which ->
            // get user input
            val newGoal = input.text.toString().toIntOrNull()
            if (newGoal != null) {
                updateMilestoneGoal(newGoal, mainIns)
                db.updateMileStone(newGoal, db.getAllInfo()[0])
                mainIns.userList = db.getAllInfo()
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
    private fun updateMilestoneGoal(newGoal: Int, mainIns: MainActivity) {
        val rootView = mainIns
        val milestoneGoal = rootView.findViewById<TextView>(R.id.milestoneNumberText)
        val newGoalString = newGoal.toString()

        milestoneGoal.text = newGoalString
    }


    public fun accessMilestone(mainIns: MainActivity, db : DatabaseHelper) {
        changeMilestoneGoal(mainIns, db)
    }



    public fun acessDaily(rootView: View, mainIns: MainActivity, db:DatabaseHelper) {
        displayAlertDialog(rootView, mainIns, db)
    }


}