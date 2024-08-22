
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindgeeks.sportsnews.adapters.adapter_TeamPlayerName
import com.mindgeeks.sportsnews.models.ResponseModel.TeamPlayer
import com.mindgeeks.sportsnews.databinding.FragmentTeamPlayersNameBinding

class Fragment_TeamPlayeraName : Fragment() {

    private lateinit var list: ArrayList<TeamPlayer>
    private lateinit var binding: FragmentTeamPlayersNameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentTeamPlayersNameBinding.inflate(inflater, container, false)

        arguments?.let { bundle ->
            list = bundle.getParcelableArrayList("playerNames") ?: arrayListOf()
        }
        if (list.size>0) {
            binding.rc.adapter = adapter_TeamPlayerName(requireContext(), list)
            binding.rc.layoutManager = LinearLayoutManager(requireContext())

        }else{
            binding.nodatafound.visibility =View.VISIBLE
            binding.rc.visibility =View.GONE
        }
        return binding.root
    }
}