package com.szcmcc.movie.bean;

import java.io.Serializable;

import org.json.JSONObject;

public abstract class BaseBean<T>
  implements Serializable
{
  private static final long serialVersionUID = -804757173578073135L;

  public abstract BaseBean<T> parseJSON(JSONObject paramJSONObject);

  public abstract String toJSON();
}