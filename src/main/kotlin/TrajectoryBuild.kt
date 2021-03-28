import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder
import com.acmerobotics.roadrunner.trajectory.constraints.*

class TrajectoryBuild(constants: BaseDriveConstants) {
    private val velConstraint = MinVelocityConstraint(listOf(
        AngularVelocityConstraint(constants.maxAngVel),
        MecanumVelocityConstraint(constants.maxVel, constants.trackWidth)
    ))
    private val accelConstraint = ProfileAccelerationConstraint(constants.maxAccel)

    fun trajectoryBuilder(startPose: Pose2d): TrajectoryBuilder {
        var traj = TrajectoryBuilder(startPose, false, velConstraint, accelConstraint)
        return traj
    }

    fun trajectoryBuilder(startPose: Pose2d, reversed: Boolean): TrajectoryBuilder {
        return TrajectoryBuilder(startPose, reversed, velConstraint, accelConstraint)
    }

    fun trajectoryBuilder(startPose: Pose2d, startHeading: Double): TrajectoryBuilder {
        return TrajectoryBuilder(startPose, startHeading, velConstraint, accelConstraint)
    }
}