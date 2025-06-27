package org.yearup.data;


import org.yearup.models.Profile;

public interface ProfileDao
{
    Profile create(Profile profile);
    Profile getById(int profileId);
    void update(Profile profile, int userId);
}
