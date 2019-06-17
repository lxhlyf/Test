package com.test.model.result.zhihu;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by 简言 on 2019/2/15.
 * 努力吧 ！ 少年 ！
 */
public class ZhiHuDetailResult implements Parcelable {

    /**
     * body : <div class="main-wrap content-wrap">
     <div class="headline">

     <div class="img-place-holder"></div>



     </div>

     <div class="content-inner">




     <div class="question">
     <h2 class="question-title">有什么是你追了很多女生都失败后才知道的？</h2>

     <div class="answer">

     <div class="meta">
     <img class="avatar" src="http://pic3.zhimg.com/v2-23b47009a3ea55e04a96bf1e6bd299be_is.jpg">
     <span class="author">广君，</span><span class="bio">徐晓蕾研究委员会前高级研究员</span>
     </div>

     <div class="content">
     <p>我是个好人。</p>
     </div>
     </div>


     <div class="view-more"><a href="http://www.zhihu.com/question/280952027">查看知乎讨论<span class="js-question-holder"></span></a></div>

     </div>





     <div class="question">
     <h2 class="question-title">染发失败是怎样一种体验？</h2>

     <div class="answer">

     <div class="meta">
     <img class="avatar" src="http://pic3.zhimg.com/v2-c2676ac36bec96dcac47fbd3b797cc1e_is.jpg">
     <span class="author">绿胖子，</span><span class="bio">我很爱你。</span>
     </div>

     <div class="content">
     <p><img class="content-image" src="http://pic4.zhimg.com/70/v2-18efc8180a21c42d697e336c5a2674af_b.jpg" alt=""></p>
     <p>世界上还有比我更惨的人吗？</p>
     </div>
     </div>


     <div class="view-more"><a href="http://www.zhihu.com/question/40805163">查看知乎讨论<span class="js-question-holder"></span></a></div>

     </div>





     <div class="question">
     <h2 class="question-title">你曾因为长相受到过什么特殊待遇？</h2>

     <div class="answer">

     <div class="meta">
     <img class="avatar" src="http://pic1.zhimg.com/da8e974dc_is.jpg">
     <span class="author">知乎用户，</span><span class="bio">喜剧迷/后摇狗/末流编剧</span>
     </div>

     <div class="content">
     <p>初中时候，不知道什么古装剧组，让学校男生演一些兵勇，其他男生都发育的差不多了，我还比较瘦小，我以为可以因为身高逃过一劫，早点回家吃饭。但是万万没想到，一个工作人员说我面有异相，骨骼清奇，力排众议让我出演了一个角色，他叮嘱我，别控制要放松，做我自己就好！！于是我演了一个小太监 - -！</p>
     </div>
     </div>


     <div class="view-more"><a href="http://www.zhihu.com/question/41017412">查看知乎讨论<span class="js-question-holder"></span></a></div>

     </div>





     <div class="question">
     <h2 class="question-title">你在艺考时遇到过哪些奇葩事？</h2>

     <div class="answer">

     <div class="meta">
     <img class="avatar" src="http://pic3.zhimg.com/v2-dc7c7574f0cbcd0328d06e72692797e6_is.jpg">
     <span class="author">苏鸢，</span><span class="bio">在黄昏放飞薛定谔的猫头鹰【如果你骂我，我就骂你】【我变懒了 看不惯直接删】</span>
     </div>

     <div class="content">
     <p>我没经历过艺考，但是我有一个播音班的朋友，当年他去暨南大学面试的时候，群面表演，然后，老师出的问题是 着火了！</p>
     <p>所有学生都做鸟兽散，抱头，蹲下，装作毛巾捂住口鼻。</p>
     <p>我的那个朋友那个时候突然走神了，愣在原地没动静，在人群里格外突兀。</p>
     <p>老师喊了他一下，说，同学，着火了。</p>
     <p>我的朋友那个时候突然反应过来，然而已经来不及做表演了，于是我朋友翘起二郎腿，淡定的冲老师一笑说：老师，这火是我放的。</p>
     <p>……最后这个学校还真的录了他。可以说是很机智了。</p>
     </div>
     </div>


     <div class="view-more"><a href="http://www.zhihu.com/question/31388155">查看知乎讨论<span class="js-question-holder"></span></a></div>

     </div>


     </div>
     </div><script type=“text/javascript”>window.daily=true</script>
     * image_source : 《蝙蝠侠：黑暗骑士》
     * title : 瞎扯 · 如何正确地吐槽
     * image : https://pic1.zhimg.com/v2-0a36771c088047f49e1549cc719a0684.jpg
     * share_url : http://daily.zhihu.com/story/9707391
     * js : []
     * thumbnail : https://pic2.zhimg.com/v2-28bf46f72d0942a93dca29e4eed69ec9.jpg
     * ga_prefix : 021306
     * id : 9707391
     * css : ["http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3"]
     */

    private String body;
    private String image_source;
    private String title;
    private String image;
    private String share_url;
    private String thumbnail;
    private String ga_prefix;
    private int id;
    private List<String> js;
    private List<String> css;

    protected ZhiHuDetailResult(Parcel in) {
        body = in.readString();
        image_source = in.readString();
        title = in.readString();
        image = in.readString();
        share_url = in.readString();
        thumbnail = in.readString();
        ga_prefix = in.readString();
        id = in.readInt();
        js = in.createStringArrayList();
        css = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(body);
        dest.writeString(image_source);
        dest.writeString(title);
        dest.writeString(image);
        dest.writeString(share_url);
        dest.writeString(thumbnail);
        dest.writeString(ga_prefix);
        dest.writeInt(id);
        dest.writeStringList(js);
        dest.writeStringList(css);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ZhiHuDetailResult> CREATOR = new Creator<ZhiHuDetailResult>() {
        @Override
        public ZhiHuDetailResult createFromParcel(Parcel in) {
            return new ZhiHuDetailResult(in);
        }

        @Override
        public ZhiHuDetailResult[] newArray(int size) {
            return new ZhiHuDetailResult[size];
        }
    };

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getJs() {
        return js;
    }

    public void setJs(List<String> js) {
        this.js = js;
    }

    public List<String> getCss() {
        return css;
    }

    public void setCss(List<String> css) {
        this.css = css;
    }
}
