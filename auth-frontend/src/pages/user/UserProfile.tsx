import { useEffect, useState } from "react";
import axios from "axios";
import type User from "../../Models/User";
import useAuth from "../../auth/store";
import { useNavigate } from "react-router";
import apiClient from "../../config/apiClient";

const UserDashboard = () => {
  // User comes directly from Zustand
  const user = useAuth((state) => state.user);

  // Only UI/form state stays local
  const [formData, setFormData] = useState({
    name: "",
    image: "",
  });

  const [editing, setEditing] = useState(false);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
    const toTitleCase = (str: string) =>
       str
        .split(" ")                    // ["aarav", "jha"]
        .map(word => word.charAt(0).toUpperCase() + word.slice(1))  // ["Aarav", "Jha"]
        .join(" ");
  const navigate=useNavigate();

  // Keep form fields in sync with the user from Zustand
  useEffect(() => {
    if (user) {
      setFormData({
        name: user.name ?? "",
        image: user.image ?? "",
      });
    }
  }, [user]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;

    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSave = async () => {
    if (!user) return;

    setSaving(true);
    setError("");
    setSuccess("");

    try {
      const response = await apiClient.put<User>(
        `/Users/Id/${user.id}`,
        {
          name: formData.name,
          image: formData.image,
        },
        {
          withCredentials: true,
        }
      );

      // Update Zustand with the new user
      // This assumes your store has a setUser() function.
      const setUser = useAuth.getState().setUser;
      setUser(response.data);

      setEditing(false);
      setSuccess("Profile updated successfully.");
    } catch (error: any) {
      console.error(error);

      setError(
        error?.response?.data?.message ||
          "Failed to update profile."
      );
    } finally {
      setSaving(false);
    }
  };

  if (!user) {
    return (
      <div className="flex min-h-screen items-center justify-center">
        <p>User not found.</p>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-900 p-6">
      <div className="mx-auto max-w-3xl rounded-xl bg-gray-950 p-6 shadow-md">

        {/* Header */}
        <div className="mb-8 flex items-center justify-between">
          <div>
            <h1 className="text-2xl font-bold">
              My Profile
            </h1>

            <p className="text-gray-500">
              Manage your account information
            </p>
          </div>

          <div className="flex items-center gap-3">
            <button
                onClick={() => navigate("/dashboard")}
                className="rounded-lg border border-gray-700 px-4 py-2 text-white hover:bg-gray-800"
            >
                Go To Dashboard
            </button>


          {!editing && (
            <button
              onClick={() => {
                setEditing(true);
                setSuccess("");
                setError("");
              }}
              className="rounded-lg bg-black px-4 py-2 text-white"
            >
              Edit Profile
            </button>
          )}
          </div>
        </div>

        {/* Profile Image */}
        <div className="mb-8 flex items-center gap-5">
          <div className="flex h-24 w-24 items-center justify-center overflow-hidden rounded-full bg-gray-200">
            {user.image ? (
              <img
                src={user.image}
                alt={user.name || "Profile"}
                className="h-full w-full object-cover"
              />
            ) : (
              <span className="text-3xl font-semibold text-black">
                {user.name?.charAt(0).toUpperCase() || "U"}
              </span>
            )}
          </div>

          <div>
            <h2 className="text-xl font-semibold">
              {user.name || "No name"}
            </h2>

            <p className="text-gray-500">
              {user.email}
            </p>
          </div>
        </div>

        {/* Messages */}
        {error && (
          <div className="mb-4 rounded-lg bg-red-100 p-3 text-red-700">
            {error}
          </div>
        )}

        {success && (
          <div className="mb-4 rounded-lg bg-green-100 p-3 text-green-700">
            {success}
          </div>
        )}

       
        {/* Profile Information */}
        <div className="grid gap-5 md:grid-cols-2">

          {/* Name */}
          <div>
            <label className="mb-2 block font-medium">
              Name
            </label>

            {editing ? (
              <input
                type="text"
                name="name"
                value={formData.name}
                onChange={handleChange}
                className="w-full rounded-lg border px-3 py-2 outline-none focus:ring-2"
              />
            ) : (
              <p className="rounded-lg bg-gray-50 text-black p-3">
                {user?.name && toTitleCase(user?.name)  || "Not provided"}
              </p>
            )}
          </div>

          {/* Email */}
          <div>
            <label className="mb-2 block font-medium">
              Email
            </label>

            <p className="rounded-lg bg-gray-50 text-black text-black p-3">
              {user.email}
            </p>
          </div>

          {/* Provider */}
          <div>
            <label className="mb-2 block font-medium">
              Provider
            </label>

            <p className="rounded-lg bg-gray-50 text-black p-3">
              {user.provider || "LOCAL"}
            </p>
          </div>

          {/* Account Status */}
          <div>
            <label className="mb-2 block font-medium">
              Account Status
            </label>

            <p
              className={`rounded-lg p-3 ${
                user.enabled
                  ? "bg-green-50 text-green-700"
                  : "bg-red-50 text-red-700"
              }`}
            >
              {user.enabled ? "Active" : "Disabled"}
            </p>
          </div>

          {/* User ID
          <div>
            <label className="mb-2 block font-medium">
              User ID
            </label>

            <p className="break-all rounded-lg text-black bg-gray-50 p-3 text-sm">
              {user.id}
            </p> */}
         {/* // </div> */}

          {/* Image */}
          {editing && (
            <div>
              <label className="mb-2 block font-medium">
                Profile Image URL
              </label>

              <input
                type="text"
                name="image"
                value={formData.image}
                onChange={handleChange}
                placeholder="https://example.com/image.jpg"
                className="w-full rounded-lg border px-3 py-2 outline-none focus:ring-2"
              />
            </div>
          )}

          {/* Created At */}
          <div>
            <label className="mb-2 block font-medium">
              Created At
            </label>

            <p className="rounded-lg text-black bg-gray-50 p-3">
              {user.createdAt
                ? new Date(user.createdAt).toLocaleString()
                : "N/A"}
            </p>
          </div>

          {/* Updated At */}
          <div>
            <label className="mb-2 block font-medium">
              Updated At
            </label>

            <p className="rounded-lg text-black bg-gray-50 p-3">
              {user.updatedAt
                ? new Date(user.updatedAt).toLocaleString()
                : "N/A"}
            </p>
          </div>
        </div>

        {/* Buttons */}
        {editing && (
          <div className="mt-8 flex gap-3">
            <button
              onClick={handleSave}
              disabled={saving}
              className="rounded-lg bg-black px-5 py-2 text-white disabled:opacity-50"
            >
              {saving ? "Saving..." : "Save Changes"}
            </button>

            <button
              onClick={() => {
                setEditing(false);

                // Reset form to current Zustand user
                setFormData({
                  name: user.name ?? "",
                  image: user.image ?? "",
                });

                setError("");
                setSuccess("");
              }}
              className="rounded-lg border px-5 py-2"
            >
              Cancel
            </button>
          </div>
        )}
      </div>
    </div>
  );
};

export default UserDashboard;