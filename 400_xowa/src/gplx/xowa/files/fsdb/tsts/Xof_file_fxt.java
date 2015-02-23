/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.files.fsdb.tsts; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*; import gplx.xowa.files.fsdb.*;
import gplx.fsdb.*; import gplx.dbs.*; import gplx.xowa.files.origs.*; import gplx.xowa.files.bins.*; import gplx.xowa.files.cnvs.*; import gplx.xowa.files.exts.*;
import gplx.xowa.wikis.*; import gplx.xowa.files.repos.*;
import gplx.fsdb.data.*;
class Xof_file_fxt {		
	private Xoae_app app; private Xof_fsdb_mgr fsdb_mgr;
	private final Fsd_thm_itm tmp_thm = Fsd_thm_itm.new_(); private final Fsd_img_itm tmp_img = new Fsd_img_itm();
	public void Rls() {fsdb_mgr.Rls();}
	public void Reset() {
		Io_mgr._.InitEngine_mem();	// NOTE: files are downloaded to mem_engine, regardless of Db being mem or sqlite; always reset
		Io_url fsys_db = Xoa_test_.Url_file_enwiki();
		Xoa_test_.Db_init(fsys_db);
		app = Xoa_app_fxt.app_(Op_sys.Cur().Os_name(), fsys_db);
		Xowe_wiki wiki = Xoa_app_fxt.wiki_tst_(app);
		Xof_repo_fxt.Repos_init(app.File_mgr(), true, wiki);
		fsdb_mgr = new Xof_fsdb_mgr__sql();	// NOTE: must new Xof_fsdb_mgr__sql b/c it keeps a local init;
		fsdb_mgr.Init_by_wiki(wiki);
		fsdb_mgr.Bin_mgr().Wkrs__clear();
		Xof_bin_wkr__fsdb_sql bin_wkr_fsdb = (Xof_bin_wkr__fsdb_sql)fsdb_mgr.Bin_mgr().Wkrs__get_or_new(Xof_bin_wkr_.Key_fsdb_wiki);
		fsdb_mgr.Bin_mgr().Resizer_(Xof_img_wkr_resize_img_mok._);
		bin_wkr_fsdb.Resize_allowed_(true);
	}
	public void Init__orig_w_fsdb__commons_orig(String ttl, int w, int h) {
		this.Init_fsdb_db(Xof_fsdb_arg.new_comm(Bool_.N, ttl, w, h));
		this.Init_orig_db(Xof_orig_arg.new_comm(ttl, w, h));
	}
	public void Init_orig_db(Xof_orig_arg arg) {
		fsdb_mgr.Orig_mgr().Insert(arg.Repo(), arg.Page(), Xof_ext_.new_by_ttl_(arg.Page()).Id(), arg.W(), arg.H(), arg.Redirect(), Xof_orig_wkr_.Status_found);
	}
	public void Init_fsdb_db(Xof_fsdb_arg arg) {
		if (arg.Is_thumb())
			fsdb_mgr.Mnt_mgr().Thm_insert(tmp_thm, arg.Wiki(), arg.Ttl(), arg.Ext(), arg.W(), arg.H(), arg.Time(), arg.Page(), arg.Modified(), arg.Hash(), arg.Bin().length, gplx.ios.Io_stream_rdr_.mem_(arg.Bin()));
		else
			fsdb_mgr.Mnt_mgr().Img_insert(tmp_img, arg.Wiki(), arg.Ttl(), arg.Ext(), arg.W(), arg.H(), arg.Modified(), arg.Hash(), arg.Bin().length, gplx.ios.Io_stream_rdr_.mem_(arg.Bin()));
	}
	public void Exec_get(Xof_exec_arg arg) {
		byte[] ttl = arg.Ttl();
		byte[] md5 = Xof_xfer_itm_.Md5_(ttl);
		Xof_ext ext = Xof_ext_.new_by_ttl_(ttl);
		Xof_fsdb_itm itm = new Xof_fsdb_itm();
		itm.Ctor_by_lnki(ttl, ext, md5, arg.Lnki_type(), arg.Lnki_w(), arg.Lnki_h(), Xof_patch_upright_tid_.Tid_all, arg.Lnki_upright(), arg.Lnki_time(), Xof_doc_page.Null);
		ListAdp itms_list = ListAdp_.new_(); itms_list.Add(itm);
		fsdb_mgr.Fsdb_search_by_list(arg.Exec_tid(), itms_list, Xoae_page.Empty);
		if (arg.Rslt_orig() != Xof_orig_wkr_.Status_null)	Tfds.Eq(arg.Rslt_orig(), itm.Orig_status(), "rslt_orig");
		if (arg.Rslt_fsdb() != Xof_bin_wkr_.Tid_null)		Tfds.Eq(arg.Rslt_fsdb(), itm.Rslt_bin(), "rslt_fsdb");
		if (arg.Rslt_conv() != Xof_cnv_wkr_.Tid_null)		Tfds.Eq(arg.Rslt_conv(), itm.Rslt_cnv(), "rslt_conv");
	}
	public void Test_fsys_exists_y(String url)			{Test_fsys_exists(url, Bool_.Y);}
	public void Test_fsys_exists_n(String url)			{Test_fsys_exists(url, Bool_.N);}
	public void Test_fsys_exists(String url, boolean expd) {Tfds.Eq(expd, Io_mgr._.ExistsFil(Io_url_.new_any_(url)));}
	public void Test_fsys(String url, String expd_bin)	{Tfds.Eq(expd_bin, Io_mgr._.LoadFilStr(url));}
}
class Xof_repo_fxt {
	public static void Repos_init(Xof_file_mgr file_mgr, boolean src_repo_is_wmf, Xowe_wiki wiki) {
		byte[] src_commons = Bry_.new_ascii_("src_commons");
		byte[] src_en_wiki = Bry_.new_ascii_("src_en_wiki");
		byte[] trg_commons = Bry_.new_ascii_("trg_commons");
		byte[] trg_en_wiki = Bry_.new_ascii_("trg_en_wiki");
		Ini_repo_add(file_mgr, src_commons, "mem/src/commons.wikimedia.org/", Xow_domain_.Domain_str_commons, false);
		Ini_repo_add(file_mgr, src_en_wiki, "mem/src/en.wikipedia.org/"		, Xow_domain_.Domain_str_enwiki, false);
		Ini_repo_add(file_mgr, trg_commons, "mem/root/common/", Xow_domain_.Domain_str_commons, true).Primary_(true);
		Ini_repo_add(file_mgr, trg_en_wiki, "mem/root/enwiki/", Xow_domain_.Domain_str_enwiki, true).Primary_(true);
		Xowe_repo_mgr wiki_repo_mgr = wiki.File_mgr().Repo_mgr();
		Xof_repo_pair pair = null;
		pair = wiki_repo_mgr.Add_repo(src_commons, trg_commons);
		pair.Src().Fsys_is_wnt_(true).Wmf_fsys_(src_repo_is_wmf).Tarball_(!src_repo_is_wmf);
		pair.Trg().Fsys_is_wnt_(true);

		pair = wiki_repo_mgr.Add_repo(src_en_wiki, trg_en_wiki);
		pair.Src().Fsys_is_wnt_(true).Wmf_fsys_(src_repo_is_wmf);
		pair.Trg().Fsys_is_wnt_(true);
	}
	private static Xof_repo_itm Ini_repo_add(Xof_file_mgr file_mgr, byte[] key, String root, String wiki, boolean trg) {
		return file_mgr.Repo_mgr().Set(String_.new_utf8_(key), root, wiki).Ext_rules_(Xof_rule_grp.Grp_app_default).Dir_depth_(2);
	}
}
class Xof_orig_arg {
	Xof_orig_arg(byte repo, byte[] page, int w, int h, byte[] redirect) {this.repo = repo; this.page = page; this.w = w; this.h = h; this.redirect = redirect;}
	public byte			Repo() {return repo;} private final byte repo;
	public byte[]		Page() {return page;} private final byte[] page;
	public int			W() {return w;} private final int w;
	public int			H() {return h;} private final int h;
	public byte[]		Redirect() {return redirect;} private final byte[] redirect;
	public static Xof_orig_arg new_comm_file(String page)								{return new_(Bool_.Y, page, Xof_img_size.Size_null, Xof_img_size.Size_null);}
	public static Xof_orig_arg new_comm(String page, int w, int h)						{return new_(Bool_.Y, page, w, h);}
	public static Xof_orig_arg new_wiki(String page, int w, int h)						{return new_(Bool_.N, page, w, h);}
	public static Xof_orig_arg new_wiki_redirect(String src, String trg)				{return new_(Bool_.N, src, 440, 400, trg);}
	public static Xof_orig_arg new_comm_redirect(String src, String trg)				{return new_(Bool_.Y, src, 440, 400, trg);}
	private static Xof_orig_arg new_(boolean repo_is_commons, String page, int w, int h)	{return new_(repo_is_commons, page, w, h, null);}
	public static Xof_orig_arg new_(boolean repo_is_commons, String page, int w, int h, String redirect_str) {
		byte repo = repo_is_commons ? Xof_repo_itm.Repo_remote : Xof_repo_itm.Repo_local;
		byte[] redirect = redirect_str == null ? Bry_.Empty : Bry_.new_utf8_(redirect_str);
		return new Xof_orig_arg(repo, Bry_.new_utf8_(page), w, h, redirect);
	}
}
class Xof_fsdb_arg {
	Xof_fsdb_arg(byte[] wiki, byte[] ttl, boolean is_thumb, int ext, int w, int h, int time, byte[] bin) {
		this.wiki = wiki; this.ttl = ttl; this.is_thumb = is_thumb; this.w = w; this.h = h; this.time = time; this.ext = ext; this.bin = bin;
	}
	public byte[] Wiki() {return wiki;} private final byte[] wiki;
	public byte[] Ttl() {return ttl;} private final byte[] ttl;
	public int Ext() {return ext;} private final int ext;
	public boolean Is_thumb() {return is_thumb;} private final boolean is_thumb;
	public int W() {return w;} private final int w;
	public int H() {return h;} private final int h;
	public double Time() {return time;} private final double time;
	public int Page() {return page;} private final int page = Xof_doc_page.Null;
	public byte[] Bin() {return bin;} private final byte[] bin;
	public DateAdp Modified() {return modified;} private final DateAdp modified = Fsd_thm_tbl.Modified_null;
	public String Hash() {return hash;} private final String hash = Fsd_thm_tbl.Hash_null;
	public static Xof_fsdb_arg new_comm_file(String ttl)						{return new_(Xow_domain_.Domain_bry_commons, Bool_.N, ttl, Xof_img_size.Null, Xof_img_size.Null, Xof_doc_thumb.Null_as_int);}
	public static Xof_fsdb_arg new_comm_thumb(String ttl)						{return new_(Xow_domain_.Domain_bry_commons, Bool_.Y, ttl, W_default, H_default, Xof_doc_thumb.Null_as_int);}
	public static Xof_fsdb_arg new_comm_thumb(String ttl, int w, int h)			{return new_(Xow_domain_.Domain_bry_commons, Bool_.Y, ttl, w, h, Xof_doc_thumb.Null_as_int);}
	public static Xof_fsdb_arg new_comm_thumb(String ttl, int w, int h, int s)	{return new_(Xow_domain_.Domain_bry_commons, Bool_.Y, ttl, w, h, s);}
	public static Xof_fsdb_arg new_comm_orig(String ttl, int w, int h)			{return new_(Xow_domain_.Domain_bry_commons, Bool_.N, ttl, w, h, Xof_doc_thumb.Null_as_int);}
	public static Xof_fsdb_arg new_comm(boolean thumb, String ttl, int w, int h)	{return new_(Xow_domain_.Domain_bry_commons, thumb, ttl, w, h, Xof_doc_thumb.Null_as_int);}
	public static Xof_fsdb_arg new_wiki_thumb(String ttl, int w, int h)			{return new_(Xow_domain_.Domain_bry_enwiki, Bool_.Y, ttl, w, h, Xof_doc_thumb.Null_as_int);}
	public static Xof_fsdb_arg new_wiki_orig(String ttl, int w, int h)			{return new_(Xow_domain_.Domain_bry_enwiki, Bool_.N, ttl, w, h, Xof_doc_thumb.Null_as_int);}
	public static Xof_fsdb_arg new_(byte[] wiki, boolean is_thumb, String ttl_str, int w, int h, int time) {
		byte[] ttl = Bry_.new_utf8_(ttl_str);
		int ext = Xof_ext_.new_by_ttl_(ttl).Id();
		String bin_str = ext == Xof_ext_.Id_svg ? file_svg_(w, h) : file_img_(w, h);
		byte[] bin = Bry_.new_ascii_(bin_str);
		return new Xof_fsdb_arg(wiki, ttl, is_thumb, ext, w, h, time, bin);
	}
	private static final int W_default = 220, H_default = 200;
	private static String file_img_(int w, int h) {return String_.Format("{0},{1}", w, h);}
	private static String file_svg_(int w, int h) {return String_.Format("<svg width=\"{0}\" height=\"{1}\" />", w, h);}
}
class Xof_exec_arg {
	public byte[] Ttl() {return ttl;} private byte[] ttl;
	public byte Lnki_type() {return lnki_type;} private byte lnki_type = Xop_lnki_type.Id_thumb;
	public int Lnki_w() {return lnki_w;} private int lnki_w;
	public int Lnki_h() {return lnki_h;} private int lnki_h = Xop_lnki_tkn.Height_null;
	public double Lnki_upright() {return lnki_upright;} public Xof_exec_arg Lnki_upright_(double v) {lnki_upright = v; return this;} private double lnki_upright = Xop_lnki_tkn.Upright_null;
	public int Lnki_time() {return lnki_time;} public Xof_exec_arg Lnki_time_(int v) {lnki_time = v; return this;} private int lnki_time = Xof_doc_thumb.Null_as_int;
	public int Lnki_page() {return lnki_page;} public Xof_exec_arg Lnki_page_(int v) {lnki_page = v; return this;} private int lnki_page = Xof_doc_page.Null;
	public byte Exec_tid() {return exec_tid;} public Xof_exec_arg Exec_tid_(byte v) {exec_tid = v; return this;} private byte exec_tid = Xof_exec_tid.Tid_wiki_page;
	public byte Rslt_orig() {return rslt_orig;} private byte rslt_orig = Xof_orig_wkr_.Status_null;
	public byte Rslt_fsdb() {return rslt_fsdb;} private byte rslt_fsdb = Xof_bin_wkr_.Tid_null;
	public byte Rslt_conv() {return rslt_conv;} private byte rslt_conv = Xof_cnv_wkr_.Tid_null;
	public boolean Lnki_type_is_thumb() {return Xop_lnki_type.Id_defaults_to_thumb(lnki_type);}
	public Xof_exec_arg Rslt_orig_noop()		{rslt_orig = Xof_orig_wkr_.Status_noop; return this;}
	public Xof_exec_arg Rslt_orig_missing()		{rslt_orig = Xof_orig_wkr_.Status_missing_orig; return this;}
	public Xof_exec_arg Rslt_orig_found()		{rslt_orig = Xof_orig_wkr_.Status_found; return this;}
	public Xof_exec_arg Rslt_fsdb_xowa()		{rslt_fsdb = Xof_bin_wkr_.Tid_fsdb_xowa; return this;}
	public Xof_exec_arg Rslt_fsdb_null()		{rslt_fsdb = Xof_bin_wkr_.Tid_null; return this;}
	public Xof_exec_arg Rslt_conv_y()			{rslt_conv = Xof_cnv_wkr_.Tid_y; return this;}
	public Xof_exec_arg Rslt_conv_n()			{rslt_conv = Xof_cnv_wkr_.Tid_n; return this;}
	public static Xof_exec_arg new_thumb(String ttl)					{return new_(ttl, Xop_lnki_type.Id_thumb, 220, Xop_lnki_tkn.Height_null);}
	public static Xof_exec_arg new_thumb(String ttl, int w)				{return new_(ttl, Xop_lnki_type.Id_thumb, w, Xop_lnki_tkn.Height_null);}
	public static Xof_exec_arg new_thumb(String ttl, int w, int h)		{return new_(ttl, Xop_lnki_type.Id_thumb, w, h);}
	public static Xof_exec_arg new_orig(String ttl)						{return new_(ttl, Xop_lnki_type.Id_null, Xop_lnki_tkn.Width_null, Xop_lnki_tkn.Height_null);}
	public static Xof_exec_arg new_(String ttl, byte type, int w, int h) {
		Xof_exec_arg rv = new Xof_exec_arg();
		rv.ttl = Bry_.new_utf8_(ttl);
		rv.lnki_type = type;
		rv.lnki_w = w;
		rv.lnki_h = h;
		return rv;
	}
}
