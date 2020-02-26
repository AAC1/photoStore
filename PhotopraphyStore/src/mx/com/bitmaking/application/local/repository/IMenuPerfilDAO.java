package mx.com.bitmaking.application.local.repository;

import java.util.List;

import mx.com.bitmaking.application.entity.Store_menu;

public interface IMenuPerfilDAO {
	public List<Store_menu> getFxIdByPerfil(int id_perfil);
}
