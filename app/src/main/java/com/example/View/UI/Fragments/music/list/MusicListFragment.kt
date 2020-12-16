@file:Suppress("DEPRECATION")

package com.example.View.UI.Fragments.music.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Model.Adapters.SongAdapter
import com.example.Model.Entities.PlayerList
import com.example.Model.Entities.Song
import com.example.View.UI.MainActivityView
import com.example.idnpv001.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MusicListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MusicListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mysongs: ArrayList<Song>
    private var playList: RecyclerView? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var songAdapter: SongAdapter? = null
    private lateinit var playerList: PlayerList

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
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_music_list, container, false)

        playList = root.findViewById(R.id.recyclerView)
        layoutManager = LinearLayoutManager(root.context)
        songAdapter = SongAdapter((activity as MainActivityView).playerList)
        playList?.layoutManager = layoutManager
        playList?.adapter = songAdapter

        return root
    }

    fun asd(){

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MusicListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MusicListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}