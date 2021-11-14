package util.trajectories

import com.acmerobotics.roadrunner.trajectory.Trajectory

data class ParallelTrajectory(val trajectory: Trajectory,
                              val segmentLengths: MutableList<Double>)