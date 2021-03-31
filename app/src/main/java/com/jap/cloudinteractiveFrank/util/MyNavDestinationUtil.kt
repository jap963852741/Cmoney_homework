package com.jap.cloudinteractiveFrank.util

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.NavHost
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import com.jap.cloudinteractiveFrank.R
import java.util.*

class MyNavDestinationUtil : NavHostFragment(), NavHost {
    val TAG = "MyNavDestinationUtil"

    override fun createFragmentNavigator(): Navigator<out FragmentNavigator.Destination> {
        Log.e(TAG,"createFragmentNavigator")
        return FixFragmentNavigator(requireContext(), childFragmentManager, getContainerId())
//        return super.createFragmentNavigator()
    }
    private fun getContainerId(): Int {
        val id = id
        return if (id != 0 && id != View.NO_ID) {
            id
        } else R.id.nav_host_fragment_container
        // Fallback to using our own ID if this Fragment wasn't added via
        // add(containerViewId, Fragment)
    }
    @Navigator.Name("fragment")
    inner class FixFragmentNavigator(context: Context, manager: FragmentManager, containerId: Int) :
        FragmentNavigator(context, manager, containerId){

        val TAG = "FixFragmentNavigator"
        private val mContext: Context = context
        private val mFragmentManager: FragmentManager = manager
        private val mContainerId = containerId
        private val mBackStack = ArrayDeque<Int>()

        override fun navigate(
            destination: Destination,
            args: Bundle?,
            navOptions: NavOptions?,
            navigatorExtras: Navigator.Extras?
        ): NavDestination? {
//            return super.navigate(destination, args, navOptions, navigatorExtras)
            if (mFragmentManager.isStateSaved) {
                Log.i(
                    TAG, "Ignoring navigate() call: FragmentManager has already"
                            + " saved its state"
                )
                return null
            }
            var className = destination.className
            Log.e(TAG,"className  $className")
            if (className[0] == '.') {
                className = mContext.packageName + className
            }
            val frag = instantiateFragment(
                mContext, mFragmentManager,
                className, args
            )
            mFragmentManager
            frag.arguments = args
            val ft = mFragmentManager.beginTransaction()

            var enterAnim = navOptions?.enterAnim ?: -1
            var exitAnim = navOptions?.exitAnim ?: -1
            var popEnterAnim = navOptions?.popEnterAnim ?: -1
            var popExitAnim = navOptions?.popExitAnim ?: -1
            if (enterAnim != -1 || exitAnim != -1 || popEnterAnim != -1 || popExitAnim != -1) {
                enterAnim = if (enterAnim != -1) enterAnim else 0
                exitAnim = if (exitAnim != -1) exitAnim else 0
                popEnterAnim = if (popEnterAnim != -1) popEnterAnim else 0
                popExitAnim = if (popExitAnim != -1) popExitAnim else 0
                ft.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
            }

//            ft.replace(mContainerId, frag)
            if(getNowFragment() != null) ft.hide(getNowFragment()!!)
            ft.add(mContainerId,frag)
            ft.show(frag)

//            ft.setPrimaryNavigationFragment(frag)

            @IdRes val destId = destination.id
            val initialNavigation = mBackStack.isEmpty()
            // TODO Build first class singleTop behavior for fragments
            // TODO Build first class singleTop behavior for fragments
            val isSingleTopReplacement = (navOptions != null && !initialNavigation
                    && navOptions.shouldLaunchSingleTop()
                    && mBackStack.peekLast() == destId)

            val isAdded: Boolean
            isAdded = if (initialNavigation) {
                true
            } else if (isSingleTopReplacement) {
                // Single Top means we only want one instance on the back stack
                if (mBackStack.size > 1) {
                    // If the Fragment to be replaced is on the FragmentManager's
                    // back stack, a simple replace() isn't enough so we
                    // remove it from the back stack and put our replacement
                    // on the back stack in its place
                    mFragmentManager.popBackStack(
                        generateBackStackName(mBackStack.size, mBackStack.peekLast()),
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                    ft.addToBackStack(generateBackStackName(mBackStack.size, destId))
                }
                false
            } else {
                ft.addToBackStack(generateBackStackName(mBackStack.size + 1, destId))
                true
            }
            if (navigatorExtras is Extras) {
                for ((key, value) in navigatorExtras.sharedElements) {
                    ft.addSharedElement(key!!, value!!)
                }
            }
            ft.setReorderingAllowed(true)
            ft.commit()
            // The commit succeeded, update our view of the world
            // The commit succeeded, update our view of the world
            return if (isAdded) {
                mBackStack.add(destId)
                destination
            } else {
                null
            }
        }

        override fun popBackStack(): Boolean {
            Log.e(
                TAG,
                "mBackStack ${mBackStack.toString()}"
            )
            if (mBackStack.isEmpty()) {
                return false
            }
            if (mFragmentManager.isStateSaved) {
                Log.i(
                    TAG,
                    "Ignoring popBackStack() call: FragmentManager has already"
                            + " saved its state"
                )
                return false
            }
            mFragmentManager.popBackStack(
                generateBackStackName(mBackStack.size, mBackStack.peekLast()),
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
            mBackStack.removeLast()
            return true
        }

        private fun generateBackStackName(backStackIndex: Int, destId: Int): String? {
            return "$backStackIndex-$destId"
        }

        fun getNowFragment() : Fragment?{
            val fragments: List<Fragment> = mFragmentManager.fragments
//            val i = fragments.size-1
            for (i in 0..fragments.size-1) {
                val j = fragments.size - 1 - i
                if (fragments[j].isVisible) {
                    Log.i("FragmentSwitchUtil","getNowFragment : " + fragments[j].toString() )
                    return fragments[j]
                }
            }
            return null
        }
    }

}

//public NavDestination navigate(@NonNull Destination destination, @Nullable Bundle args,
//@Nullable NavOptions navOptions, @Nullable Navigator.Extras navigatorExtras) {
//
//// ...省略
//    if(mFragmentManager.getFragments().size()>0){
//        ft.hide(mFragmentManager.getFragments().get(mFragmentManager.getFragments().size()-1));
//        ft.add(mContainerId, frag);
//    }else {
//        ft.replace(mContainerId, frag);
//    }
////      ft.replace(mContainerId, frag);
//    ft.setPrimaryNavigationFragment(frag);
//
//// ...省略
