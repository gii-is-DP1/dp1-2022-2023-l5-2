/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.bossmonster.user;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.bossmonster.game.chat.MessageRepository;
import org.springframework.samples.bossmonster.gameLobby.GameLobbyRepository;
import org.springframework.samples.bossmonster.gameResult.GameResultRepository;
import org.springframework.samples.bossmonster.invitations.InvitationRepository;
import org.springframework.samples.bossmonster.social.FriendRequestRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {

	private UserRepository userRepository;
	private GameResultRepository resultRepository;
	private GameLobbyRepository lobbyRepository;
	private FriendRequestRepository requestRepository;
	private MessageRepository messageRepository;
	private InvitationRepository invitationRepository;

	@Autowired
	public UserService(UserRepository userRepository, GameResultRepository resultRepository, GameLobbyRepository lobbyRepository, FriendRequestRepository requestRepository, MessageRepository messageRepository,InvitationRepository invitationRepository) {
		this.userRepository = userRepository;
		this.resultRepository=resultRepository;
		this.lobbyRepository=lobbyRepository;
		this.requestRepository=requestRepository;
		this.messageRepository=messageRepository;
		this.invitationRepository=invitationRepository;
	}

	@Transactional
	public void saveUser(User user) throws DataAccessException {
		user.setEnabled(true);
		userRepository.save(user);
	}

    @Transactional(readOnly = true)
	public Optional<User> findUser(String username) {
		return userRepository.findById(username);
	}

    @Transactional(readOnly = true)
    public Optional<User> getLoggedInUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return findUser(username);
    }

    @Transactional(readOnly = true)
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

    @Transactional(readOnly = true)
	public Page<User> getPageUsers(Pageable pageable){
		return userRepository.findAll(pageable);
	}

    @Transactional
	public void deleteUser(String username){
		resultRepository.setWinnerNull(username);
		resultRepository.deleteParticipated(username);

		invitationRepository.deleteAfterUserDedge(username);

		lobbyRepository.setLeaderToNullBeforeDelete(username);
		lobbyRepository.deleteJoinedUserIfUserGetsDeleted(username);
		lobbyRepository.deleteParticipantsIfLeaderIsDeleted(username);
		lobbyRepository.deleteJoinedUserIfUserGetsDeleted(username);

		requestRepository.deleteFriendRequestWhenUserDeleted(username);
		messageRepository.deleteAllMesagesFromUser(username);


        userRepository.deleteById(username);
    }
}
