package com.example.duksunggoodsplatform_2021_android.goodsEx

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.duksunggoodsplatform_2021_android.ActualFormActivity
import com.example.duksunggoodsplatform_2021_android.InterestListActivity
import com.example.duksunggoodsplatform_2021_android.R
import com.example.duksunggoodsplatform_2021_android.api.ApiRetrofitClient
import com.example.duksunggoodsplatform_2021_android.databinding.FragmentGoodsExBinding
import com.example.duksunggoodsplatform_2021_android.goodsEx.modelItemDetailData.Data
import com.example.duksunggoodsplatform_2021_android.goodsEx.modelItemDetailData.ModelItemDetailData
import retrofit2.Call
import retrofit2.Response
import java.text.DecimalFormat

class GoodsExFragment : Fragment() {
    private var _binding: FragmentGoodsExBinding? = null
    private val binding get() = _binding!!

    // 가수요/실수요 구분 값. actual 이면 실수요, fictitious 면 가수요
    private val codeTypeActual = "actual"
    private val codeTypeFictitious = "fictitious"

    private var fav = false
//    private var price = -1
//    private var category = "NoData"
//    private var type = codeTypeActual
    private var imageList = arrayListOf<ModelGoodsExImage>()
    private lateinit var imageAdapter: GoodsExImageAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentGoodsExBinding.inflate(inflater, container, false)
        val view = binding.root

        //TODO : itemId 임시. 원래는 -1
        val itemId = arguments?.getInt("itemId") ?: 1

        var purchaseCount = 1

        if(itemId == -1){
            Toast.makeText(activity, "잘못된 상품 정보 입니다.", Toast.LENGTH_SHORT).show()
            Log.d("GoodsEx", "잘못된 상품 정보 입니다. : itemId = ${itemId}")
        }else{
            callItemDetailData(itemId)
        }

/*        //더미데이터
        val dummyImageUrl = "https://bit.ly/3yAt3za"
        val dummyImageUrl2 = "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxMTEhUSEhIWFRUXGBYYFxgYFhoeGxggGhcYFhcdFxkeHiggGB0mIBgYITEhKCkrLi4uGx8zODMsNygtLisBCgoKDg0OGxAQGy0mICYvLy0tLi4tLS0tLS0tLS0tLy0tLS0tLS0vLS0tLS0tLS8tLS0tLS0tLS0tLy0tLS0tLf/AABEIALQBGAMBIgACEQEDEQH/xAAcAAACAgMBAQAAAAAAAAAAAAAFBgAEAgMHAQj/xABCEAACAQIEAwcCAwUHAgYDAAABAhEAAwQSITEFQVEGEyIyYXGBkaEjQrEHFFLB0TNicoLC4fCishUWJEOz8TRTdP/EABoBAAIDAQEAAAAAAAAAAAAAAAMEAAECBQb/xAA5EQABAwIDBAoABAYCAwAAAAABAAIRAyEEEjFBUWFxBRMigZGhscHR8BQy4fEGFSMzQlJysiRDYv/aAAwDAQACEQMRAD8AfKlSvKKuQquLbSqPC8QLa4i8fysv0RO8P6mrOObSqGDAOGExFx7rEEAyAWQyDoRlFVsWhqFoTid5bCM2IJNzCNeJFtAbeZQqZWAknM436UwYfiWS4ML4rlxVsrcabclmRSTkJU+pidzQEYLDi61o4ZrVq6MKFe1ZuQxNwXHVmVWQCRbBED82orzh2IP7496/h4W5ea9nv4S6LloBPCLd5M9uAEG5HP2oV08S06hEsa9m/fNlbrBilxZBTKCcthlVXUMXIAIgkaGN9SNzhlySyXFE7/hxrMmWDHzZUBkSANKEdn+IC9inui/avBYVoyxAy3LP7upl2iWVi0ak66VXtYi4MS7IFu3LX7y7FbXdtda3CKDlchwXbSACSppWpgsNUdmfTBO+PhGFV7eyHER92gI1gOHvnHeSiqzsAHQqxd7rsSSMxXxgDy6jUUUe2OenvS1h+1Ls9tbiuUzOc1uzdGdVtga29Wyq7wTtpVWxi0R718G+ptDF3ryG5dCGXKWVKE5dc2bQchRcPSp4emKdMQEOq01XS835fdvqml8MKWeOeHEIo5KSdOpga/Bphwl+4dLlrJtli4r5pBnQAFYjWetK3EHz4u6dwIQfAAP86aBlIuYA4IxgjpV6qeDXSrtRYKlSpUq1SlSpUqKKVKleVFFR41je5sXLvNVOX3Oi/ciuQmnv9ouNhbdkcyXb2Gi/ck/FIbUJ5vC6WEZDJ3ph7D9nkx157LvcTKmcMigjQgEMTsTOnsaab/7MbIkDEXZBO4U/aB+tA/2SXwvEQpNyWtuAF8sgBvxP7sAx6xXWsYPE3/OVYGqO4lck4h+z6+km3dtuB/FKH+Y+9Ldzhl5TBt/RlP3BNdl4u0Wrh28La/FJ2C4XmBe47WkUwfDrCot05j+TMjHIYMkRppOHlwIDU1hcOa0kmANv7SSTsgJPtcIvt+QD3Zf61vfs/dCs5ZIVWYgMSYUEnl6U0J+5AuGuahmysrXCCmW4UK6+csUBB0AA9YLcNt91bElb65PxwuUm2HUmcwMOoUqCDuScpMECu1NyPAp2v0Y6m2WyTuIyzyNx4xqBqUsJ2Ef82IQeyk/zFbcZ2Ps2bee7iHM6IqoAXJ2Amfr6E7AxexPFGw15bJcXbbqHsOSs5ZAhnLy5HXLJEGSZodice166CQpfKQigqbxkjyBfKpygyW5axFOtpsN143EdIVqbiwCXbABE+/MDTboVlwLsRdxANxLYe2JUNcuG2HIgOUyqzEBg41A2EE1rxHDDYu9zd4bcVzOUyLi3In+yMzcaATkUFo5V0rshxC1a4ZZa6y2lw9pbV7MYyPaARwfUsJHXMImRQHjHaK/jUa3Zw1pMO0fiYoMWaNQyWVIKawQzMCNDANIw6o6wXbbUFFozmY2nU/eCC9nMHhrlxzii+FSQlrw90rGYcu5GZGzeEI2WYJ8U+FzucBvKCbGNcz5Ret27iAehQW3j1LGhPCXLi3g8bcLXIixihobpykd3eBkFhuJJzgb5gZ8u9l8VYP4JUry7m69kmd5tT3Z+WqoLDGiYaaVYBzXRzFu63qJ5ph7McUDo3eZUdbly0wzaZrbtbbITEqYkc4IqVy/G8Jxdp7z3rNwKWZ8/dJdutml21CXLe8jKSg03JNStzN0RmHYR+fy/VdErw17WLU2vNIXxS5Ck9ATVRDFqwh//AFqWHq2prztE34TgbkZR/m8I/Ws8U9sXCNyoVdTEQo+1UdFoa/fuxFMCsjTfXUbnkJP+ZqJrb1keuvPaRry+o2rn2D7edzdZWUXLWYw1td13EAwr6actefV+4JxezesrfQAAgnK510OUgakbiKyjtcDt01WeJ4ZbuSLtu3cmQcyKxO4Ilgd4qhwnDYW25FgBSRBVTcyRmVtpyfIHXrFbeLYnvCHsojggjMGBVch8UwCd2Ggn2rdwtFswgks7eYCBsCIHIaxHvrWMzc0bUY06gEx2fuznvVLH4R2uPeDuCcObVvu2IuKWZ7zFWiNcltRHrS7h8JiDhXRjedrpRr63UWye8IVma3cYEXR+GqwQBB36vHFLYRZcqI6ttJAGum8RQV7yNmKEfEaHcTB9B9K1CrMRYrR2f4hebO+JzShJlhZGhWdDZcgxB1IHmFBOFSxLnckk+7Ek/rRniVzLhbzTq0IP8zRofQP9ulUeE29BWxolnGXE/fuiO4ddK31rtDStlWhKVK8rwtUUXte1oa8Kx/eBUUVipWkXhVPjvEO5w9y5OoWF/wAR8K/ciorAJMBc77UY3vsTcYHQHIvsun3Mn5oQ1e14aXXZa0NAATB+zfGC1xKyWuC2rB0YtADZl8KydiWCR6gV2viI8fwP6V8+9nrxTGYdwiuRdtwrGASWAEnlBMz1Art/a3vA1s27z2wQwOVbZmIic6NG/KKtolyxVeGDMUJ7Ts2VLawS9wCDHigM2XUgeIhV1I3pY4ncbEX7eHabeVczKGDQwy5kWZKjaFJaJbbYbu1Peva8ThwpzDwgNqIOqwpGs+UUp4excQJfAYIHgMBoSBqBm8MwRVkQ667eBaanR76uGPbGYNAF88Ta/wCYtiLSNQRcJ2tcDw4Ed0p9y0/WZ+9C7GOt4a9ctH8SzOdVctGeAwzKvmPmSYIkgkaGqh7QXmEO5C8shYE/4vAsfBrRh+C3LqG6qnIWVBI8xnQAgFARmG59pgtRKoZGzuXP/h38Y/EO/ENqtpxBL8zRmm0Zjd3EaCZMKcR42YJFhHXMSrMisLWbVhtAUnNAM0JbE99ft3LVoK4IDLbGVdgA09YjSOQ16neE3zbzB7c2ihZyRoYnQA+bpA60l38cQWu2k7sEllCkwND1JOwGv0ocQr6Ry/jKrKdu1EzqSAHTfWZ/eU/8Iw37zee/c/skcKiyYuXLYym5cGz5DKqdefpRLEYxLuMsYA3WtG8rOXVVZtJKKM0hZysSxB8sRrIIdlsOlmxat6grbUa9coLH3JJNU+MYBRjVxdp0W8bRsozSe7JzkvaUaPcyFxB0G+sRWySOyBfWVyGU6bh173CBIA22038484gLVYW3+6f+oZcozZ3JygkOZuAz4SWGcEGQSI5UY4V2muW1UYhXvWoBW8qHvQIkd9ZgMxGniUSeajcrWJ4T3iYe5h2YJaQZLLyoIiAcxGZLkbMQfiSaacJgPwlJLAwD4zJHox1k+s1KobHbWcMKjnHqb6kg7L6XvP7HRFl7XYACWxlhNJh7gR/lGhwfSKlc97dqDawzz4kvKuvIMtwQDz1IPxUrAwrdp8lT8e4RlZMjf+hTtWFw6VlWq+dKMlkv8Z1a2vW4n/Sc5/7aEXMV+K7TEsT9zRXFa37ek5e8uHWPKsf6qXrIJaSsySBGjaEiZ9iTH60J9YNsjswj6pDptpod3peJ2bdqt4HhdlrverbFzNmYiMy7ZiVQAkmFOgBMtVvh2Mt2ixtWgysUzd2ozSQVAYAKAshhHI9CaH37kKfHDszHUeWICsQpnkCY3k/AyxjDZuHK1pXAyFovAsASBA1VhHXl10oIqNL888E06k9tI4exMzbUTe+luN07PxPDN/Y3FsuBlIlrbHqgUiBtuBpvygnbGKsojW0uZn3Y5gSTsMwB0jQDpXO3xWYfinD3OYY5gwHSUUGPQipgcYqXBlxCpGmVlJVhI0loI02M/FaFXMb/AK+B+UN9EtpnKDadkjjcAebRxO1NeNxz4hUOaVBOTadDlbOsTvEdIIOp0AjH3labCC40nPmnLAJ1EsN9BPKD61hiRc7xjb7oK3iDMSZM6lQBHWRMTrzM54CV8M5iSJaAJ+PQVvLLiZsh1aobTDYh3nB0m0aReZ4XsU47elLFvYk5mEzBAiAYEiWHLlVzhlvShWLfPiPRVVfk+I/YrR7AJpRUAGWyr616TUrReuRVrKye5yFDsZxixafJcLuw3S2AcscmYkCfQTHOKIYhu5tO+pulGIC6sgKkKQIPiJgD56GuMYLieJslrig6MVZiJg7EHofEPrQqjiNF0MJhqbpNTw0Xb+zXFsNiVZrKZQjZDnAmY6ySaJXSrZuQRucjywT8fak39lWDvLhbr3kYC7cLLBIMFACdxEn/AJrTbaYKSqoY3LEGSdYyliS5/lW2kkXVVQGOIbpK0uttrZdrRXQnVCrbTsNfilTtfwi7fsKcOZAOcowIc6GAJG++hppW8QCpliTrIQZZiBAOnpzrTduz/Fz0zD29/X+dEyg6pbrA05gLhcQmDB0I0IO46zXtNvbPhYcm+g8YBLgScwBgkyB4hp/vSgjUu5paYK6VKq2o3MFg4WRnBKyMwBgkT4gDyMTrXfeO4m22GtXWm2rAFc8ArmTMA+u8DrXAb40rtWJzYjhFopcOKuBbQZkKyW2cHUDw5oI305mqZGcShYoE0nZRJiw3qlibciDBBEbggg1StYjVrap31hT3ZjzBQuY21A1K5yhZh4jlg6ilzF8FxJDKmHKjbWzAE6TJaG15BDTNwu3+6WfEjBVgAaSSdDJ5ajXmCRprRKhBeGBC6Prvw9N9YiG7Q4EabdhBbsI7wqLWMEAxNtsyg5pVwDNiAUB0AF4E6xo3TSsE4yt6/bsWw1oXLahhbDgOpghYzNIBDANpppA2poxWKQpbuNcy27qlQoUHU+ZidNEJHQmI2M0NfCmznw2c2yqBrTBpLwMxz3IBYnxFdgDIAGgqCgZuV0MR/EzsnZac2glwdEiQbgAZtW5gbxAFiF7tjwm0Ld+5h2ZRaK5lAJUnMqJcQDSSCxK5TIE5hzC8D7P4zEYZR3luxhnBBvXoDPo05dMz6AncDfU0+XjZuXrK4jIjXli9ZuIYfKAZOmVJADQcplSRNC+0vanBi+LlgO7C21osuiRBHgJMAjUSF22NVUyt0SOCdVqt7QJMyDGo1FjJBixkTI26q3d4kbV1rZVLlpe7RbuYqCWC+IwG/DkkZhzB6Gq+J4sl17dq5hyvjSLlu9JR+qtlK7NlgxMnkdFi5xuw8juXUEZfBdC6ZQGDeAhwSAx9fet+AwdvEEW7LhWZVGS7pDDWbbLy6Dze9CNRx2poYRjBJZ5edj+6fOBcPtvZa7Yd2LO5fvIzsR4JJAAjKqx4RpFZm6wBWSPSt/BrbWbK2nIZ4AJBMac4Ow+/WqWMukS0rG5JMCmWXEFcrEdgl9MkciRZKnbi5Jw9vpca8fZFKj7uKlCuI4xb1+7dA1X8KddQPGMvQHNNSjsAi64+Je4uAYYgeO2fAxyC6nVbEHSrNUsY2lLrphKvF7YdrpO62gFMkQXYg6j2FLlviCWyufQgnMMwg7ldBqCPXrR/i39jfY/muKo6wqgnUkQPNQLiNtRbZXEypYZUBJC76QSBsc06RyodXLZpR8IKmZz26A7LxePOOOq8xXGUuQizYUsGYgKyEajVg3OQTzBArGzfQ6LiBAGzWDB9Z8X0iKC4PBo8G1eVbgOitIB/wsRqdYIogneWp77C2tB5zbYD5NsFfqKWymI87x4hNuNN7w4jtboaHdzXAzzaeQRcXwBKNbXbx2rpttvzV4B9QIoVijbfwFs7jY5iHbTYzmUgRoQdI51ZwQs3yYsxI1KMwCn2Jj4P0qtxK+uHPdi2207rrPrJJ2I16fNC65pqGmYkX58QPm6K+lW6kVqTXEkxpBaRsdOzUANhs2JBhE+C3FWxqoRwcrApGYSADoANtJ61as4woxfumZAYlRB9IB33n5FDOE8StOPClwkefyxH5YM9Yo5hECW7jtaYNJRT5pgSc2pjUjU60am5w3xylBfSY9mao1gdGgJbskA7jH7hWeEtnY3IjMS3i6cp+AKasMulL/BrMACmK3oKcSBEABbmNaMMM91VOw1Px/wV6QzaKCesDassPYCHMrSxVTBEFeev/ORq1bRtKOJbXUxq0SeZ0gVXfDWlUytsAkE6AyZkaDQmdaF8S4ldzLasLmca3CdFAAmAZkkk6ATsZq/hmYIuYjOQJ2iY1jmaoGUw6QJKsMzbkhRyUDX56H68qHcRtI3ma5IZWBk+EqQYgHUHmKzOJXMfGCRmlRBYkenp09ap4rGpazHORzZmOgHQHZfg1EKfFZXr7QVBgEtlgHQDqrEE/GnrQvFwylWcBdJyMFLehBEL6wZ9YodjuMq2JCrdIUAi4sGSxAKhiy+EASYBE5h1FU8VxB0uEHEAoTIBABQbaNGvPf61rMsmm7vjmrYGUspPlIYBx/cgZiIK8xpvyEGkPieH7u8ywADDKAZADagA9BqPimfF3g0EO5DDQlkhujbDnO5I396pY3hou3EzlgchBVdxrMT4tNZBjnyqnNJEIlGq2k6Tode7gO9Lb12b9lneHhniVAgd+7y+YjNLG5rvmzfAFcy7TcGWzkuWWLWnUEZvMp5gkadD80+/sZdDhMQq22DZ/FcJ8LyvhC9Co3EfmBnWAu4Earohwe0OGiaeJmLb6T4TIkieokaj3pOGGuWUaB3lrMmdQJ6Mmg2zCPqQabeJYlFGVj5lY7cgJNKqcUKqQoYGMpYE6rmz6Cd5O/TpW+v6s3uPhK18EMRDgYcLTwIM22jhtBI4olY4Y69/gyM1kkFHMkqQMwKqAWcwIMDkdd6wLMVIJBFtcqMJFzKJBzsVAtRqsKS3hMPoBQ7AcQgyPDERrBkTBEGRuR7E1tbFhz5gdSQBtMcvWI13PU0J2M7Mlt/Lx4qM6LyOhjoGgOpAmQ0DSGk2JzbOSqX8JY7613doBEcMWa3uPFIO7XD4tWbmvPMTXi9ocDbkZcoHLuenxW8s8kKs/fl9KxuYHvDNy2CQAJYDYcok0P8AEHWAmmUGSWy43+3M+Z9gsf8Azfw1oDK3vlMD2A/pWGGscMxha1ZK2rpMo4UoxO40MBx6DXQUSwHBsNKFsPbPmaSo/J49tiI3mq+M7I4VbcLbtozZwzZnLStxh4QWKjQAbDnVCpIk+6YFMNIDSQd4/ZZ43vLd3urj52hZPlDaCSx5Us9qsTiHxCWbCG6jIADlYJnJ8PiAPLkfQ6TTpirDFcO3egC2uU+BSeYSTA0K7DQaTvrRTDPDGSdBaJnQyqeIkAwD+H9qN14EZVz/AMHnLus0M8P2hcn4DwDF4ie7tMM7F2u3UZLQ1CfhhhmeBGnoZIqV2LBYcokH2+hY/wCkV7Rm1HkC8cln8FQBPZniYPtu3QEPND8e2lEDQXjl3Lbduik/atpEb0rcS4Sb9rzusqxUAyDLMxzJudGAka/SlCxYvoxRcpbWQzHXlBWZ2zaRsTTrheIIyoJAIUKVb0Eadaz4thVvCdVceUjbmIOu2vpWXsudizQxJDRMHu05HW6WbHZ9bwLIGtNqIBlD1gGGjT0HvVT92xNojuhnyEqCkkjbMrL9oIpjTClmliohQo7olZ21OWNY/wCaCrFxFXKAksYRdYPTVunvQhSMlxty2eyZdjGiKbAXnTtXB3Ro6dkSRvlB8Bxru2i/aVDKyqiMxYN4iu2mUBvEPMu2494i/eXptW7Jt5lBcohMQCSrBhB1iMvrziijcPV2a3dtW80lTO40J1iZ0G4NKvFuz9+2yIIu6alQQSAdAyzpuRMnTTlQstJr5Bub802HV6mHLHNAaLXNhwubQdhMTbRH7723TwsHzeGFEnaYiZB35ctqKjDZbNuXZi0rBJ01nX18IGutLPDbmJZg7WstslVY5RA8WSYJnqpJPWm7F+dEkEiWJCgeYwNB/ho9IzJJlJYlgYWtDcs6gGZjWbncivC7egoleeBVXh66VljW0oiG7VVsZ2xwuHtMq3Q91QSVGYSZkrMROnWt2A4pbxAS/an8RQZPoxUiAI0PpXPb3ZzEYmMyqipKxP4jxMZF2gmBJI+Yo+AcFhsGpE5A63FVg0FyWIB5mZI5fFZa5xFwmsUymz+26dn31Tqj6k8zHX9QPfSvL+I8JYASFIBjWSORkH6fWhTcXth1slznKF428I0mY59Kj8RG5gcgSxgg7enLnWkrJEFV+F2O5JQHO5XNso59TGpk8+lbcRfAQrkKzMQWnXc6DTTSeX3rRdvg+UgA6SGGm2wOh2jQRvVPiBVkIYnJGgAInlG/iH6zUFgqc7M+fvsq95StpyjO7MrQHZmLRsY2XT13+/N+Jcaa4PGxLbdNuUU+lwEyqRbBB08RBnoNm+IBpVxnZ/CtmYM4OojMIB6mRIPPLNDc3MncNXFPNPjtVbsvxR3NwXPHbVV80ELqAB9AdqY+D3nuYpUtXBJEvKSqCdQoG+0axyoHb4YLVorbtsZgkkwTEgSOWkmKNfs/sqLt66wIUjKh/KYILfciPmitkCEGvldLh7Ir2ot3FUW3fMXJLAahRlhVUnY7mRHP1ot+xLFkrftm9IBBWzA0nzPMTqYETpHqKGPxHNdYlR3aAuASZmGCx9Yjp7V7+xPEHv76eDxIranxmCRC9VEmfUr1oVUBMYVxLSDwXQOL2cwUf4hGYCZHrvttSzd4W0koFIOu50mP9vrTrfw6sfEAcpJHpykdKTO0XbbB4WUtgYi7tlQ+EEaeN9QNthJqnAOF00xwH391nb4EyiWy6kmB6R1HofrWh7eUxIhSwkDfWJn4rnXF+1GKxJkstsdLS5fq3mP1j0oU4dvMzN7sT+tAdSB0t5/CKKm9dVu8Qtp57yr7uoqk/aDCr/76H2Yt+k1zVcN6VsGHrPUA6krXWnYAuk2O2GEysO/gzpNttQylbkHISNlHLnWvFftDw0kZbr+Jz4bVuBmMnKztm/6RXPBh6yFitCi0LBqEpzu/tHXKFGFuGDMtiAJkKCIFvQeERqYqxhP2l2sxZ8LcQnfI4bef4o6naN6Re4qGxWw1o2LJJO1dlwHb/A3t74tHXS4Co103Ph59alcXNipW8yzlXeTQ/G2pBohWm8KMuKEk8R4QhMhY+32oX3F235HMdP8AbauqcO7O27yC47tqTosDYxuQau2+yGD3Nsv7u38iBUDyEb8G2oJIHp6LjrcScf2iI3uNfqKzPELd2A1tpmRlMx7cx8V2uz2ewi+XDWvcoCfqZohatKuiqF9gB+lTrBuWf5duefCfhcUwPAWcg28PdOsgvZbSTycjT60Tt9iMU0n92tgn8zMJ+pJPWut1KzKYGDbtcSub4XsRjiALmJtKAI2YtGnQgT8aSau3ex9u2+e9iiM2wFvYLAOsnQTuRzp7pf7QWSRpmJJuATJ3WYHp4KoblqrTa0ZgJI0me/SNiG8U4YMMUCsWVp3iQRHT3ofiRIq9xcv+54V3MmYn0MlZ/wAoFVLWorQSz4kECJAPihVy011TbW61u4PKw6AaD4j9KEcUxbgvbxIGUqzKyRLOAsbxHl+5AmjHErBGo0I2IoTxiw7XbbyDkKsk7Sd5GobbfQiT1qQtNy6HTzQ7gmNW5eZypBFoW4KkFhJJMbEHTlrFaOGJexF65kLBwxWF0ypPOPNBn6nrFGL65ymYkE7ORqCeWn5aDYy1cs3O8S53VzUT+Vtdfv8A1oVRjo7OqJhqrA+HC0QNsfeSuXi+GfuS5RnIAzZmtsQJKqIi1oDsNfSs8RjgIJLLsAckoehAPzseXpQzFnE4i5be9dTLbYMFC9Nen6mrt680EFtPQmR00y7xzn61umXR2tVmuKZdLbqrj5uAZ3KyCe5BUnSYJMbHpHKtIIUASSQP4o9hlXl71sS6qLkBA30Gn10J/wCCh13F5j3VqGY7CAFGm5MzHvrWpVQSIGirY66fKg/EuHKJ313Op0HpTCvE2wtq2iWDcVdJjQHm/wDePX+UCqFrgASXOe9dz2w2RZyrIN2FGsRp1pq4licKtq5fN1CSv4aK4MDKABAmNT8UKrVcwgNCPSoU6rZds7u/fy02pc4tjRZU7G7dB56KraZgOU0D4Xjb1hi9i4bblSuZYmDBIB5bCtDtnYty0A9gAo/StyrVuMlMUqYYIW7GcWxV0EXMTecHcG68H3WYNUrdirtnDM/kUn9PrtRHD8EP52j0X+tRtNztAs1cRSpfndHDb4aoOtutgSmK5wC2fLcI05/yHM0MfhL/AJWVvTY/StGi8bENuOoO2xz+bjzVLJUy1tuYe4vmQ/r9xWkNQyCLFNNcHCWmRwWWWpFQNXs1FakV5XtSoovMtSvalRRdtqtiTpVmquK2o64YRfsye8w7pvludeoU/Tf70Y4fbKqQVC+ImJnekfhXGmw3eAIGzFdyRET033+1Xv8AzNin8ltQNdQhO0TqTGkj6iqgpttem2AZkbgnSakUi3cdjiYa4VkEx4F0G+wn/wCjVLEG6VLPfLaExmJnxZdAT+lVlVnF7mnvsuh3b6r5nVfdgP1qnd45hl3vr8HN/wBs1zUCa32sHcYwLbE6flPParyIH45x/K31Ke17SWCYQsx9Fj9YrTjuLoy+KwWA8XiYDb2nr96VuHYS8D4FgsuhkRE5f1gRvWeMtXUTM7CDGg9Z9B/DPyKw5rpsnaWJpdVNVpJvNreZHkt3EeONiU7kWlQA5lgknw7AbDnFacA8iqvC18RPSP8An2qwwyXCBoDqPnf7z9q3tSYDnUg9xk/qt2LtSKDvbkhHIAB8JPLmRPqYphGoodjcHNWsA7Ch2K4YLiaHLlILZY6EA7EnSqr4e6ykm2rqNpYSQOnvE68/rVl3vWxCOQszGkVTuX7lwnwDmSVB0994ETr6VkuDRJV9U51m34fdVWw3Cle2HW3cTWdGgR/2/FacZg0UEG7cG2UQvinYTAg6VswOMezcbPcUhojO5hJAzFeU7yfvVi7ZFxiUYMDJUqVI1JnUaazVgyqLSPuiBr2bDOHe82TLqp35jQiP060SwuBSwQ1u3B2kzP8AiJ20j9awxeFuG2QhzPmUZnMKpgwyjYkE9OnSaF47jVyxFpsjsNCB9QzmSTM6DT1qpARwx7xr7I5cvmwM8KrlpzGZYzIHX/6pM4jdViYABYktHMlpk+v9TWOM4ncumTA0gxqT11NarNqsl6YoUMlzqvbSxTZ2e4bZa2rXELOxaOYEGB4ee1K7CnXs5cFtbJaQAgOm4zAmR0PikGDy0ouGaHOM7kv0nWNKk0AxLgJ4bVcGEQ6JcX28v+1T/wAPcESCATuNRrpqRV+9jLRDG4FfPJWNcuhImdRqV5bT7UHtX2XysR7Gnw3MF551VtNwOvL4M67O0ssRwQXAQLzFdCVzZQ2uxWMxEjZWFVsfw1l2tnae7GYCZ3OrCNNioII32ogMeT51De4g/UVvGNQiMzp85h/Whmg5P0ulgBEjlEfH/ZLlnvVXNeCgGTmI6ny5tFBHTU67VtOHDiXtgakCcpMAkSCNwY0IJBFGmwWbVHVvTY/Q1XvYVh5l+tTIdunJVVxFNzczacHeD529ye9A7vB0O2ZfYz+tVLnB3HlZT7yP60exEqpIUsQCQBufQUnXO1L5icqKNsuWcpEjU7mdOkRHrS9YUmHtDwTWCfjKwPVuBA/2+z7Kzcwtxd0Pxr+laM9brHaZo8Sox5ZdJ9/EYqzh+Lpf7xVs5nRGuZXI8WWJCnefigEUjo7xC6GfFUx/UpW3hw9CT6qkGqVpwPELV2c1t7fqjAjadn9On0qUMxvCOXuH+DvCfMSPNd0qpi20q0aHcQfSjrkBCLh1o7ZwqjIvfMM2QiIAg6uRA0iCJnU9aBc6uXMSoEB2IAgbgbaaEkAT6VZQmPAJcfvmFeW1ayhn8THMfFc1Anw7sOXpWvA3LaEkldZyjKWidANunOdOhqrbGbyWmO+o99Py1aXh2Iby4eP8RP8AqaKpEa7QtHgD7ALyxilQtqfENIUKAYgc1BjXTLXmM4wwl0tqSAMsmW8IgBSsQOcdSat2ez+K/upoBuNojkDyreOyLt/aXhrvAJ+5isPmOyRPG/x6orW1dMp8h6oBh+0bPlKzaYKAVAmMpmddpOvXassVjmdQrGQNtAOUU0WOx1obuT7AD+tXLXZnDD8hb3Y/yiowwO0ZPKPlQ4es7h3z7fCU+FqYJAJ5fYf1q3jrJKZipUrqJj2I/n8UW4jh0svltqEUrOnUGCZPpFUMQ+YOnMrp6yGiOu1Sbp1tOKYYdyr4S5Iq0yTQvhtyQKLrW1ziqGJwoNDcIBbvAkwreFj6H/cCmBxSr2zvd3h3YGGPhX3PT2En4rL2gtIOiLRc4PGXWUX4rhC40BbNov4SXAEB1E6Gek+lK13DBTKK8bKGCpI2JI1n60e4FfOJsW3BCkqc7EwFyg5yx5AQTRO/wvCZMwx1vMTlzEqFLeHQmZXzLz5jekzgRNneS69PG1CD2JjW/wCnylbAW0UFtO71mdCI5zux9dtK51efvLj3D+ZifqdB9KdO1dxrNq4jaOW7sj/u+wP1pQsLRG0G0tLrAxLq9yIGxZparYFr0CvTW1paLtORWIUclAHwIpTw6ZriL1ZR9xTnYvKGJdc6kaiY3PI/H607g7BzoXB6ayuNNjnQJMnWNBMKo+JtrcvC9d7u3byZQpAe7nXNmXPMqNdFGsr6ms8PdlbQcqLjhTk5wzEJp+UsAGAMTOnKph8BbR+9t32zeUNiLQvgQcwyGQyET6++tbeK4K7eS6ti7nD3RdZO7PescoQFiX8SKFJULJkqCOdH6wg30tcgjhu79bmAIC0cPhK7RSa5s7ACJ+fu9a0cESCCNNQdNRI+2tZ1VWxctIuHNxcJcYu7Ne70N4oULa8GW4VAAJUmDJBEgC7cUiCVKcssz5JTzfnnLmzfm350ZtUEwO778TG1crHdGHDtzgyJHMTt8baD4xFWLOLuLoG+P9qqmmLslgiXNxkOUAlSRpMxp8TVYms2jSNR2zZvKSwdB9as2mwwTtE2G+36c1We1cjNcwxj+IAj+UVRbsdYxsuLRnmx0E/4gRJ25dKf7nEEWDJM/wAIn+E/6l+tY8NxaPmVEygaj1zSxIjTnPzXn3dK1HUyerHO8cbb/nQL19PoV9N3WCo7LF9Adn+Qi3MHZe65ZxD9mKrpFwdCrqf1BmgJ7M4jCXlu2AbgXdWIBYGQynYQQd/6V3nHYYXLbIeY09DyI+aWF4LiwN1b0OU/qP50XDYvD12HrcrDzieU2+jeg4gYqhU/p5ntI/5RzjKeUX1EWXC74ey7F7DW1J8pmAJkAN+YD+VSuyXbOpW7Zykb6x9jofipThwTXXn73GFVPpxwEZfUeRYT5plaqBVWuoreUsAdY3Mb1fahONfKQ3Qg/QzWENsTdNdvguHH/tA+8n9TVu1h7a+W2g9lFZTQnjXGxh7uHtlJW9c7stmjJqoGkGfN6UJzoEldalQzOy023vu2CfZG81aGNxtoQdTqfgbUA4jxy6mNGFt922ay7KCdc4DkKxnQeHpzoUnFQbeXEXcSMSSdEvIiAz4chDBAu3mk76GhuqgJ2ngqjgHGLgEDUwZEwYEAggmbJ2Fpudw/QV7hbwMgXFeOhGnvBpJxv7wbGGa9cW9btuTiEzBC+xUS+UXcoOusEnWdx7cxdi7ibFywq2O6aXZMudlkeAW7JckHUEtHOJ511t9N3n5W5ogwJcPzT+a4AIGWYmYd2otA0IN7gN2I4xZRjbDM7rqy2ka4y9MwVTl+Yrdw3iVq+ue04cAwdwVPRlOqn0IpQwGKfDYi/Zw470u7XSrWnDrJgmSVDrJEGQDuDV7sVcVnxTQ/eNc/GZ8ijOCYRbas0AZm1kz1NU2qS4Dn5cdqqrg2MpOcJsGkcQdZaQCBrBkzFpBkX+1eHDKs7GQduUNz9qXsReKm3lKyWVevhkBtesGdqau0KTaB6MPuCP50lcYZe5ZlbKyzlMGASCJ6n/hjSiusJXPDiSG/Z+x4KcP0JHQkfQxRu3tS7wssIDaNAn3gTrzphsnSjAyFyqgh0LOue/tHxUulkflGdvdtB9gfrXQietce4zi++vXLv8TEj2Gi/YCsvNkfCMl87vdM37KeKor3cPd8jqxHoGGV/wDSfk06P2eQWw16/aNvKivrIcKLKMM28MttoA2JETrPG+G4w2L9u7yVvF6qdG+xNPfEbiqC58oBafSJq6ZtqmKtV1IkAfmEfKWf2g8UGIxjldFBmPcDf1yhfqaCWhWk3C7s7bsST8masoKEblMMblELKvDXteNVLS38HWb6ehn6KTTJauFTKkg9QaA9n1m6x6Wz9yB/WjddLBt7B5ry3Tb/APyGgbB6mUawHFAqMhYgySIUEGRlOxERpHLSNKtuuFus76eXwpJWG16jL8T+mq4DXtHNITIskm4x2UNeA4Df+/tCMX8Eba2wuW4LgYm0yBl0AJMEEHfcDkddDQ0kABUtpbAJOVECiTAJIXc6AfFaal26FBdjAALE+g1JqNpNBk6+Hvu3rFTFVHMNNhIaf8Zkd06SbwIHC16Nvis5W7q+LLXO6W+UPdMwMZQ3voDzIPSuvYdgUUr5coj2jSuDW8bfNm0r3WGDbEi6LGVfBmuEoxeJiTJWY1rp/ZTFXAxXNNtQWKxO3T5ri45v4qgagcOwT3i3xbv0Xo6FD+W4ltFzCOta08jf5ObdaxTTZwlsMsIJlRMaxPX20qx3RgEPoWWNtUcgA+hk/aqV6940tayxQMQCAJhvCwYMIBGv+8Y2sM6xmU2hlYk5zCC0VPOZWTmEfMyaRo02gEVGyZ1sY0tc2XVrdY4yD5mfT30RBbT5ZDA6NOmxUkDToSPj508e6PBlJOedMpEAaMSdRoYEetVLcwSLhAyEh2ZQmVi2YsSNCWkkESCBttWWEYtLLLLbzJAAMElXYBp8fI6be9XVp0i05ad77DrbcTvPhxuNoqgiTu3d/v4jur9oGRE71idCFgEa7x7xMxUrbj8PbxCd2eYLIYP5TlJHsTBHrUpvo7G4alRyVyZBtbZqNn3lC5XSWExlStmoBsECZ3/fsqsaE8SXQ0WND8eulNKgmnh93Natt1RT9hQHj/BjjbyKc6W7Cse8Gha448GTqBAJI56e1/s5iM1hRzUlT8GR9iKJzQXNDhBXaw9d1IiozWLHdIifDw11SnieE4hsXhbzWklC633QqFcMAC8GCcwZpETIPKCdC9mr5hrvcgqxysbtzQaeVXV1UmJ0jYU6A0M7R2neyyrk2JliZkajKBz96E+kACRc+/gmT0hVDbAWEbd7jv8A/ojlZU+B4TD4b/073bT3CzXVBgsoKrmMnyzkH8INTBYrBYS+0YkZsW3eQWU2xDMNGAhQSzDU65T0pS4XwtluG60AkBSI192jY7fT1qjj+BrcxHeO5ZWZc1sDxRoCqkHnryETzqmh2QENAO7ckh0gaj39c6A7XjcECOd9l4XWBw63337xl/Fy5M0ny9ImPtW/D4VELG3bRCxzOVUAsdTLEeY6nfrXloiBG0CPblWwGjwFovcbE8O7cqnHf/x7h6DN9DNJnEcJ3tq7b8WqsAeQ5eEU3doLkYe4P4hl+v8AtNKOK4oLdpHOpYaD1yFhJ/KDBE1CRcFWGGGuG/4Wp1C3BGxVT9o/lRjDnSl5MS1zLcYRMgb7aEb7+Y6+lHsKdK20y0LmYkRUIKG9r8b3WFuEGGf8Nf8ANof+nNXLabv2iY3NdSyDoi5m922+gH/VSjWHm6cwrMtOd91purRXE8TzYNbc+Och65V1B+kD60PIrDJVAwjOYHROwysbSVuFYgVlVLalYvWVYXKiiK9nV0uN6oP1J/lRarXZXgofh7XROfvHYf3goVdvg17/AOGXonu/jc6dFnWulhnt6uJXlulKNU4guykggRAlDMfixattcbUKJjryAHqSQKWXv4i7rcvNbn8lrw5fQtuTTBxzBtdsOiebQrPVWDAHpMRSs/EQCVNu5nGhTIZB/T5pfpB9UEBkxw3p7+H6GFqBzqsF02BvbgOayvYi9YAdL9xiSqhbjZw0mNjt8dK3cRv4q6hts9oqSJ8LKTBmJB2NV7Fh3cXLoiPIn8Pqx5mrQvDNkmDE68x6dYrnfiqrRlDid69D/LsK5+c0wDaLQbfdvuvL+OvMrK9hWDAg5LmWJ6AjlR/sl21XDlP3lLqwuW42QlSOoIlidAaWcPjsyO5HknYzsoaPfWK3WcWCoYjL4sp1mDMb9Jj6isU3mm1zMog2IvfzKLjKQxpY+o8y27Ta2/Zodxn1XXh2rwIy5eJ2lEhlXNaIEeUNK5wB0LAxoIolglDqrWcRbuW8tyIAZXNyO9zMreJSdYEEFiZO1cH7tVvnMoi6BEgeZZkfIM1dwWexcF7CubNwc18rcodD4WHxQMrh/mdOf39FRougxHp9t6ru7JdJJOQqQB3YlRo2fNn1OfNrMRy/vVpt2mEZ7OZVud5aW26yhgCGLhQQYzSNRmI1Akr/AGZ7dWr9r8YG3eTw3VAlZjdeeU7ifbWKL8X4oDhTcssRmgKdQd9Y6aCoynis7Q5sZiACRa/GNNsDdPNGpjKLA+4JaCSAe1bhNt19/GVvsM6XC16FQLfbQQqm7ct3WGefxNQY8KnRpBzCJSPicW9weJ2aBpJ2+tSumehg69V5ngLbtsrhu6fc4/0qYjiTPl+vNPFUsaNKu1VxI0oqIFV7OY3u7zWz5XiPQ8vrt9KbJrnWLEP/AM/5zpj4Lx8EBLxhtgx2P+LofXaqITVCuGnI7u+PhMYNCONlQZdhkIIIMxzmIIOoooDQftfbY4Z2RVZrcOFYSDl1gep2HqaGdLJ4ATdBbV7Dt5ba6DQ67f3VYmfiiL4tUAVQBmXZFidAeXLWue4/tWwBy4a0cp0kGACJH8uVFr/aNFRoAyJkErsRcIUkgD8pbahBxQ3UCwyNOHPfZdQwLfhpH8I/SrINDcOzBFCKIy6TOnSqfFeKzNu37Ej9F/rRXEAI9Om6o6AtfHMaHbKp8K/c8/8AnvSllGbxcgsDc+Fjb0HoGb60aW0x/Kao3cKr27qsQkMRn0lQSrHXlQmkkmU3iqYbTaG7CPeVSw+KD8oKldZmZDTr/iDUfwjiJJ0G9CsWLdsBEXzEtmnU65v9Z+9Vu0OP7vCPB1fwD/N5v+kGjskNuuPihnq9nbp4wkrimMN69cun87Ej0Gyj6RVSpUoadAiwUqVKlRWpUqVKiila7prZWi+dKii6X2bdVwuEtMYQqbjTMHM7OAfTUV5fwRV8yYohhcLFgJBmSVA2iWnflHOrV/DBbKLqBbQA5YmAkc9I0jXqK0XeJJbJU6kmSTqJ0uNBA0aFMRvBiORQ/LBOke9/jwVOw7CCA45pk9mf+MeuusixW7jvDzAJ1cruBuVgNp0jbptXMb4IxGJB3W8yn0KgKR8EEV0hcZbshbjXQLKHMzMCMo0BkROrQIPURvXNbt9bl7E3bZJS5ib7qSCMys5KmCAdRFCxD3dQGnx3qYbCU6eNfUY6ZGzZeSDx0PCY4nKjyvg2S0LiW2gW8wNuTMEOT4fUczPSgNW+Eqhv2lu+Q3EDeoLAH4pAGLrquaHC62O+Gtq9sFUUWVzOtsEFvHPdqyEEnSdjt0qytvChWt+DISSv4eoBtIIOkEFs5nkY96vdt7HEVxFx+H3rlyyrC01lFBFl8itk7srlZSrKQwnzQdRWGL4dft4S1iMYtu3iQ7JlQKJBXMjOq+BWEMYXcFZ1milxDQZCUY5r3RBWvENhnKiLeXL5smqN4fEoNojMDMTM67UOxuJRbao4CvmgMtsw41hnOQG2x/hkivReUEZTGaWP90wQv0JJ+lY8SdWFtVgTew/hDlp8cMZPWRpWWmeyUZ4ygvGo9t6y7K2Xa7fuBWCEKoMHxFZkgbwJifWml3dgoJJCzl9IifbferHEuM8MQstu+zMrspUpdysZyQtwW40O2sGaH2OIpdSbd1XgnNBMrppmB1U6HQ9K7+FNPIGtMxv14rwXSrK5quq1RGbdMW0E28+cC07cOsuB1In661K3WzNyegM++WP1NSl8bWyPA4fK63QOBFXDucf9j6BOdacRtUqVhDS3xDzD5qsKlSrCHV/MiXB+KXUdUDSpjwnUfHT4pzva22n+E/pUqVh+qewLiWkFcfxGHUFlA0gfbwj7UNx6DuG/zfpm/Wvalc3avQDRdf4liGWwigwGiaG2bhER1qVKZf8AmUwoHVKzbck7ke3odKV+09wi3cjmyT9z/IVKlRn5lMUIplb758Fr/n5D/QUvdsrhmyvLKzfOle1KOdFx/wD2t7/dLtSpUoaaUqVKlRRSpUqVFFK8sIDcQHYss/UVKlRRdfbc1qOATPZiRmAmDH6VKlbGnj6FTHEjKRvHqEh9tMWwud1PhgXDJJJOwkkyQOQper2pSOI/uFPYUAUWxz817WFypUoSOU/9hLKYv8a+ma5ZNshszjOVC5GuqGy3GWdGIkQNdKD9tMczXzZgBEckATqz6sxkmTy6DkBUqVCbpekB1iXayw/9th//AOnD/wDyrUqVqn+cc0Wt/bdyU47YVcRirYEqMTiFg66LfcAfShwZpugO4KW8ysHYNsTlLAyyyNjMaxFSpRpILoSNVrXU25hOieuzl9nw6XGMs1tCT1JialSpTeL/ALncPRB6DthBH+zvUr//2Q=="
        val dummyImageUrl3 = "data:image/jpeg;base64,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"


        // TODO : 서버에서 데이터 받아오기
        val dummyModel1 = ModelGoodsExImage(dummyImageUrl)
        val dummyModel2 = ModelGoodsExImage(dummyImageUrl2)
        val dummyModel3 = ModelGoodsExImage(dummyImageUrl3)
        */


        // TODO : 서버에서 받아온 데이터 세팅
        setFav(fav, false)
        //activity?.let { Glide.with(it).load(mainImage).into(binding.ivGoodsExMainImage) }


        //이미지 RecyclerView
        binding.rvGoodsExImage.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.rvGoodsExImage.setHasFixedSize(true)
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvGoodsExImage)

        imageAdapter = GoodsExImageAdapter(imageList, requireActivity())
        binding.rvGoodsExImage.adapter = imageAdapter


        //관심 별버튼 클릭
        binding.ivGoodsExFavorite.setOnClickListener {
            fav = !fav
            setFav(fav, true)
        }

        binding.tvGoodsExCountMinus.setOnClickListener {
            if(purchaseCount > 1){
                purchaseCount -= 1
                binding.tvGoodsExCount.text = "${purchaseCount}"
            }else if(purchaseCount <= 1){
                Toast.makeText(activity, "최소 구매 가능 수량입니다.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvGoodsExCountPlus.setOnClickListener {
            if(purchaseCount < 99999){
                purchaseCount += 1
                binding.tvGoodsExCount.text = "${purchaseCount}"
            }else if(purchaseCount >= 99999){
                Toast.makeText(activity, "최대 구매 가능 수량입니다.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnGoodsExFavoriteList.setOnClickListener {
            val favIntent = Intent(activity, InterestListActivity::class.java)
            startActivity(favIntent)
        }


        return view
    }

    //서버 연결
    private val homeApi = ApiRetrofitClient.apiService

    private fun callItemDetailData(itemId: Int) {
        val itemDetailData = MutableLiveData<ModelItemDetailData>()

        homeApi.getItemDetailData(itemId)
            .enqueue(object : retrofit2.Callback<ModelItemDetailData> {

                override fun onResponse(
                    call: Call<ModelItemDetailData>,
                    response: Response<ModelItemDetailData>
                ) {
                    itemDetailData.value = response.body()
                    //Log.d("로그detail---", "성공 : ${itemDetailData.value}")
                    if(itemDetailData.value != null){
                        val data = itemDetailData.value!!.data
                        setItemDetailData(data)


                        val imgList = arrayListOf<String>()
                        for (img in data.imageList) {
                            imgList.add(img.url)
                        }

                        val bundle = Bundle()
                        bundle.putString("title", data.title)
                        bundle.putStringArrayList("imgList", imgList)
                        bundle.putString("description", data.description)

                        val goodsDetailFragment = GoodsDetailFragment()
                        goodsDetailFragment.arguments = bundle


                    }else{
                        Log.e("로그detail--", "ItemDetailData.value가 null임")
                    }

                }

                override fun onFailure(call: Call<ModelItemDetailData>, t: Throwable) {
                    Log.e("로그home item-error--", "실패")
                    t.printStackTrace()
                }

            })
    }

    private fun setItemDetailData(data: Data) {
        Log.d("로그detail--set-", "Data : ${data}")

        val priceFormat = DecimalFormat("#,###")
        //type = data.demandSurveyType

        for (img in data.imageList){
            imageList.add(ModelGoodsExImage(img.url))
        }
        imageAdapter.notifyDataSetChanged()

        binding.tvGoodsExCategory.text = data.category.title
        binding.tvGoodsExTitle.text = data.title
        binding.tvGoodsExPrice.text = priceFormat.format(data.price) + "원"

        //binding.tvGoodsExTotalCount.text = data. //모인개수
        binding.tvGoodxExTime.text = "남은 시간\\${data.endDate}일"

        binding.tvGoodsExInfoEndCount.text = "목표개수는 ${data.minNumber}개가 모여야만 결제됩니다."
        binding.tvGoodsExInfoPayment.text = data.description

        binding.btnGoodsExForm.setOnClickListener {
            var formIntent = Intent()
            when(data.demandSurveyType.title){
                // TODO : 가수요조사폼 뷰 생기면 연결
                //codeTypeFictitious -> formIntent = Intent(activity, FictitiousFormActivity::class.java)
                codeTypeActual -> formIntent = Intent(activity, ActualFormActivity::class.java)
            }
            startActivity(formIntent)
        }

    }

/*
    //좋아요 조회
    private fun callItemLikesData(itemId: Int) {
        val itemLikesData = MutableLiveData<ModelItemLikesData>()

        homeApi.getItemLikesData(itemId)
            .enqueue(object : retrofit2.Callback<ModelItemLikesData> {

                override fun onResponse(
                    call: Call<ModelItemLikesData>,
                    response: Response<ModelItemLikesData>
                ) {
                    itemLikesData.value = response.body()
                    //Log.d("로그Likes---", "성공 : ${itemLikesData.value}")
                    if(itemLikesData.value != null){
                        val data = itemLikesData.value!!.data
                        setItemLikesData(data)

                    }else{
                        Log.e("로그Likes--", "ItemLikesData.value가 null임")
                    }

                }

                override fun onFailure(call: Call<ModelItemLikesData>, t: Throwable) {
                    Log.e("로그home item-error--", "실패")
                    t.printStackTrace()
                }

            })
    }
*/


    // 관심 컨트롤 함수
    private fun setFav(fav: Boolean, statusChange: Boolean){
        if(fav){
            binding.ivGoodsExFavorite.setImageResource(R.drawable.star_clicked)
            if(statusChange){
                // TODO : 서버 정보 변경
                Toast.makeText(activity, "관심목록에 추가되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }else if(!fav){
            binding.ivGoodsExFavorite.setImageResource(R.drawable.star_nonclicked)
            if(statusChange){
                // TODO : 서버 정보 변경
                Toast.makeText(activity, "관심목록에서 삭제되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GoodsExFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                GoodsExFragment().apply {
//                    arguments = Bundle().apply {
//                        putString(ARG_PARAM1, param1)
//                        putString(ARG_PARAM2, param2)
//                    }
                }
    }
}